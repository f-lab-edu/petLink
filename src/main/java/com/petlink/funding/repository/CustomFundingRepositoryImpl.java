package com.petlink.funding.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.domain.QFunding;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CustomFundingRepositoryImpl implements CustomFundingRepository {

	private final JPAQueryFactory queryFactory;

	public CustomFundingRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Slice<Funding> findFundingList(LocalDateTime startDate, LocalDateTime endDate,
		List<FundingCategory> categories,
		List<FundingState> states, Pageable pageable) {
		QFunding funding = QFunding.funding;
		BooleanBuilder builder = new BooleanBuilder();

		// 필수 필드 조건
		builder.and(funding.startDate.goe(startDate)).and(funding.endDate.loe(endDate));

		// 선택적 필드 조건
		Optional.ofNullable(categories)
			.filter(cats -> !cats.isEmpty())
			.ifPresent(cats -> builder.and(funding.category.in(cats)));

		Optional.ofNullable(states)
			.filter(state -> !state.isEmpty())
			.ifPresent(state -> builder.and(funding.state.in(state)));

		List<Funding> results = queryFactory.selectFrom(funding)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(1L + pageable.getPageSize())
			.orderBy(funding.id.asc())
			.fetch();

		boolean hasNext = results.size() > pageable.getPageSize();

		if (hasNext) {
			results.remove(results.size() - 1);
		}

		return new SliceImpl<>(results, pageable, hasNext);
	}
}
