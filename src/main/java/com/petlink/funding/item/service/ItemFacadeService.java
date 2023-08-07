package com.petlink.funding.item.service;


import com.petlink.funding.item.domain.FundingItem;
import com.petlink.funding.item.exception.ItemException;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.orders.dto.request.FundingItemDto;
import com.petlink.orders.exception.OrdersException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.petlink.funding.item.exception.ItemExceptionCode.ITEM_NOT_FOUND;
import static com.petlink.orders.exception.OrdersExceptionCode.CANNOT_BUY_NOW;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFacadeService {
    private final RedissonClient redissonClient;
    private final ItemRepository itemRepository;

    // 주어진 펀딩 아이템 목록의 수량을 감소시킵니다.
    public void decrease(List<FundingItemDto> fundingItems) throws ItemException, OrdersException, InterruptedException {
        for (FundingItemDto item : fundingItems) {
            FundingItem fundingItem = itemRepository.findById(item.getFundingItemId()).orElseThrow(() -> new ItemException(ITEM_NOT_FOUND));
            RLock lock = tryLockItem(item.getFundingItemId(), fundingItem.getTitle());
            try {
                fundingItem.decrease(item.getQuantity());
            } finally {
                lock.unlock();
            }
        }
    }

    // 주어진 아이디에 해당하는 아이템에 락을 시도하고, 락 객체를 반환합니다.
    private RLock tryLockItem(Long itemId, String itemTitle) throws OrdersException, InterruptedException {
        RLock lock = redissonClient.getLock(itemId.toString());
        boolean available = lock.tryLock(20, 2, TimeUnit.SECONDS);

        if (!available) {
            log.info("lock is not available : 락 점유 실패.({}) : {}",
                    itemTitle,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            throw new OrdersException(CANNOT_BUY_NOW); //현재 구매할 수 없습니다(락 점유 실패)
        }

        return lock;
    }

}
