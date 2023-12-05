window.swaggerSpec={
  "openapi" : "3.0.1",
  "info" : {
    "title" : "Petlink API",
    "description" : "Petlink API description",
    "version" : "0.1.0"
  },
  "servers" : [ {
    "url" : "http://localhost:8080"
  } ],
  "tags" : [ ],
  "paths" : {
    "/auth/login" : {
      "post" : {
        "tags" : [ "auth" ],
        "operationId" : "member/login",
        "requestBody" : {
          "content" : {
            "application/json;charset=UTF-8" : {
              "schema" : {
                "$ref" : "#/components/schemas/auth-login1294563024"
              },
              "examples" : {
                "member/login" : {
                  "value" : "{\"email\":\"id@google.com\",\"password\":\"password1234\"}"
                }
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/auth-login851787074"
                },
                "examples" : {
                  "member/login" : {
                    "value" : "{\"token\":\"dummy-token\"}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/fundings" : {
      "get" : {
        "tags" : [ "fundings" ],
        "operationId" : "fundings/get-list",
        "parameters" : [ {
          "name" : "startDate",
          "in" : "query",
          "description" : "조회 시작일",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "endDate",
          "in" : "query",
          "description" : "조회 종료일",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "category",
          "in" : "query",
          "description" : "펀딩 카테고리",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "state",
          "in" : "query",
          "description" : "펀딩 상태",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "page",
          "in" : "query",
          "description" : "페이지 번호",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "size",
          "in" : "query",
          "description" : "페이지 사이즈",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/fundings-918017013"
                },
                "examples" : {
                  "fundings/get-list" : {
                    "value" : "{\"content\":[{\"id\":1,\"title\":\"Test Title 1\",\"state\":\"SCHEDULED\",\"category\":\"FOOD\",\"startDate\":[2023,1,1,0,0],\"endDate\":[2023,12,31,0,0]},{\"id\":2,\"title\":\"Test Title 2\",\"state\":\"PROGRESS\",\"category\":\"TOY\",\"startDate\":[2023,1,1,0,0],\"endDate\":[2023,12,31,0,0]}],\"pageable\":{\"sort\":{\"empty\":true,\"unsorted\":true,\"sorted\":false},\"offset\":0,\"pageNumber\":0,\"pageSize\":2,\"paged\":true,\"unpaged\":false},\"totalPages\":1,\"totalElements\":2,\"last\":true,\"size\":2,\"number\":0,\"sort\":{\"empty\":true,\"unsorted\":true,\"sorted\":false},\"numberOfElements\":2,\"first\":true,\"empty\":false}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/fundings/{id}" : {
      "get" : {
        "tags" : [ "fundings" ],
        "operationId" : "fundings/get-by-id",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "description" : "펀딩 아이디",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/fundings-id1140910618"
                },
                "examples" : {
                  "fundings/get-by-id" : {
                    "value" : "{\n  \"id\" : 1,\n  \"managerId\" : 1,\n  \"managerName\" : \"Test Manager\",\n  \"managerEmail\" : \"test@manager.com\",\n  \"phoneNumber\" : \"012-3456-7890\",\n  \"title\" : \"Test Title\",\n  \"miniTitle\" : \"Test Mini Title\",\n  \"content\" : \"Test Content\",\n  \"state\" : \"PROGRESS\",\n  \"category\" : \"FOOD\",\n  \"startDate\" : [ 2023, 1, 1, 0, 0 ],\n  \"endDate\" : [ 2023, 12, 1, 0, 0 ],\n  \"targetDonation\" : 1000000,\n  \"successDonation\" : 500000\n}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/fundings/manage/create" : {
      "post" : {
        "tags" : [ "fundings" ],
        "operationId" : "funding-management/create-funding",
        "requestBody" : {
          "content" : {
            "application/json;charset=UTF-8" : {
              "schema" : {
                "$ref" : "#/components/schemas/fundings-manage-create2003668828"
              },
              "examples" : {
                "funding-management/create-funding" : {
                  "value" : "{\n  \"managerId\" : 1,\n  \"title\" : \"펀딩 제목\",\n  \"miniTitle\" : \"펀딩 소제목\",\n  \"content\" : \"펀딩 내용\",\n  \"category\" : \"FOOD\",\n  \"startDate\" : \"20210801\",\n  \"endDate\" : \"20210831\",\n  \"targetDonation\" : 100000\n}"
                }
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/fundings-manage-create39299276"
                },
                "examples" : {
                  "funding-management/create-funding" : {
                    "value" : "{\n  \"id\" : 1,\n  \"registeredAt\" : [ 2021, 8, 1, 0, 0 ]\n}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/fundings/manage/image/upload" : {
      "post" : {
        "tags" : [ "fundings" ],
        "operationId" : "funding-management/upload-image",
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/fundings-manage-image-upload204345988"
                },
                "examples" : {
                  "funding-management/upload-image" : {
                    "value" : "{\n  \"id\" : 1,\n  \"link\" : \"펀딩 이미지 링크\",\n  \"name\" : \"펀딩 이미지 파일명\",\n  \"uploadedAt\" : [ 2021, 8, 1, 0, 0 ]\n}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/members/signup" : {
      "post" : {
        "tags" : [ "members" ],
        "operationId" : "member/sign-up",
        "requestBody" : {
          "content" : {
            "application/json;charset=UTF-8" : {
              "schema" : {
                "$ref" : "#/components/schemas/members-signup1017061083"
              },
              "examples" : {
                "member/sign-up" : {
                  "value" : "{\n  \"name\" : \"TestName\",\n  \"email\" : \"test@example.com\",\n  \"password\" : \"password\",\n  \"tel\" : \"1234567890\",\n  \"zipCode\" : \"12345\",\n  \"address\" : \"TestAddress\",\n  \"detailAddress\" : \"TestDetailAddress\"\n}"
                }
              }
            }
          }
        },
        "responses" : {
          "201" : {
            "description" : "201",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/members-signup-779979142"
                },
                "examples" : {
                  "member/sign-up" : {
                    "value" : "{\n  \"id\" : 1,\n  \"name\" : \"TestName\",\n  \"email\" : \"test@example.com\"\n}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/members/withdrawal" : {
      "post" : {
        "tags" : [ "members" ],
        "operationId" : "member/withdrawal",
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/members-withdrawal-455553110"
                },
                "examples" : {
                  "member/withdrawal" : {
                    "value" : "{\"message\":\"회원 탈퇴가 완료되었습니다.\",\"code\":\"WITHDRAWAL_SUCCESS\",\"result\":true}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/members/duplicate/{name}" : {
      "get" : {
        "tags" : [ "members" ],
        "operationId" : "member/name-duplicate",
        "parameters" : [ {
          "name" : "name",
          "in" : "path",
          "description" : "이름(닉네임)",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/members-duplicate-name-1386254058"
                },
                "examples" : {
                  "member/name-duplicate" : {
                    "value" : "{\n  \"message\" : \"사용 가능한 이름입니다.\",\n  \"code\" : \"AVAILABLE_NAME\",\n  \"result\" : false\n}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/orders/guest" : {
      "post" : {
        "tags" : [ "orders" ],
        "operationId" : "orders/create-by-guest",
        "requestBody" : {
          "content" : {
            "application/json;charset=UTF-8" : {
              "schema" : {
                "$ref" : "#/components/schemas/orders-guest933516009"
              },
              "examples" : {
                "orders/create-by-guest" : {
                  "value" : "{\"fundingId\":1,\"memberId\":null,\"payMethod\":\"BANK_TRANSFER\",\"amountOpen\":true,\"nameOpen\":true,\"fundingItems\":[{\"fundingItemId\":1,\"quantity\":1},{\"fundingItemId\":2,\"quantity\":1}],\"zipCode\":\"12345\",\"address\":\"서울시 강남구\",\"detailAddress\":\"테헤란로 427\",\"recipient\":\"홍길동\",\"phone\":\"01012345678\",\"subPhone\":\"01012345678\"}"
                }
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/orders-member1541140646"
                },
                "examples" : {
                  "orders/create-by-guest" : {
                    "value" : "{\"orderNumber\":\"20210701-0001\",\"orderId\":1,\"fundingId\":1,\"recipientInfo\":{\"name\":\"홍길동\",\"address\":{\"addressInfo\":\"서울시\",\"detailAddress\":\"관진구\",\"zipCode\":\"123456\"},\"phone\":\"01012345678\",\"subPhone\":\"01012345678\"},\"orderedRewards\":[\"리워드1\",\"리워드2\",\"리워드3\",\"리워드4\"],\"orderStatus\":\"ORDERED\",\"isAmountOpen\":true,\"isNameOpen\":false}"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/orders/member" : {
      "post" : {
        "tags" : [ "orders" ],
        "operationId" : "orders/create-by-member",
        "requestBody" : {
          "content" : {
            "application/json;charset=UTF-8" : {
              "schema" : {
                "$ref" : "#/components/schemas/orders-member-1397712321"
              },
              "examples" : {
                "orders/create-by-member" : {
                  "value" : "{\n  \"fundingId\" : 1,\n  \"memberId\" : 1,\n  \"payMethod\" : \"BANK_TRANSFER\",\n  \"amountOpen\" : true,\n  \"nameOpen\" : true,\n  \"fundingItems\" : [ {\n    \"fundingItemId\" : 1,\n    \"quantity\" : 1\n  }, {\n    \"fundingItemId\" : 2,\n    \"quantity\" : 1\n  } ],\n  \"zipCode\" : \"12345\",\n  \"address\" : \"서울시 강남구\",\n  \"detailAddress\" : \"테헤란로 427\",\n  \"recipient\" : \"홍길동\",\n  \"phone\" : \"01012345678\",\n  \"subPhone\" : \"01012345678\"\n}"
                }
              }
            }
          }
        },
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json;charset=UTF-8" : {
                "schema" : {
                  "$ref" : "#/components/schemas/orders-member1541140646"
                },
                "examples" : {
                  "orders/create-by-member" : {
                    "value" : "{\n  \"orderNumber\" : \"20210701-0001\",\n  \"orderId\" : 1,\n  \"fundingId\" : 1,\n  \"recipientInfo\" : {\n    \"name\" : \"홍길동\",\n    \"address\" : {\n      \"addressInfo\" : \"서울시\",\n      \"detailAddress\" : \"관진구\",\n      \"zipCode\" : \"123456\"\n    },\n    \"phone\" : \"01012345678\",\n    \"subPhone\" : \"01012345678\"\n  },\n  \"orderedRewards\" : [ \"리워드1\", \"리워드2\", \"리워드3\", \"리워드4\" ],\n  \"orderStatus\" : \"ORDERED\",\n  \"isAmountOpen\" : true,\n  \"isNameOpen\" : false\n}"
                  }
                }
              }
            }
          }
        }
      }
    }
  },
  "components" : {
    "schemas" : {
      "fundings-918017013" : {
        "type" : "object",
        "properties" : {
          "number" : {
            "type" : "number",
            "description" : "페이지 번호"
          },
          "numberOfElements" : {
            "type" : "number",
            "description" : "현재 페이지의 요소 수"
          },
          "size" : {
            "type" : "number",
            "description" : "페이지 사이즈"
          },
          "last" : {
            "type" : "boolean",
            "description" : "마지막 페이지 여부"
          },
          "totalPages" : {
            "type" : "number",
            "description" : "총 페이지 수"
          },
          "pageable" : {
            "type" : "object",
            "properties" : {
              "paged" : {
                "type" : "boolean",
                "description" : "페이징 여부"
              },
              "pageNumber" : {
                "type" : "number",
                "description" : "페이지 번호"
              },
              "offset" : {
                "type" : "number",
                "description" : "페이지 오프셋"
              },
              "pageSize" : {
                "type" : "number",
                "description" : "페이지 크기"
              },
              "unpaged" : {
                "type" : "boolean",
                "description" : "페이징 되지 않았는지 여부"
              },
              "sort" : {
                "type" : "object",
                "properties" : {
                  "unsorted" : {
                    "type" : "boolean",
                    "description" : "정렬되지 않은 상태인지 여부"
                  },
                  "sorted" : {
                    "type" : "boolean",
                    "description" : "정렬된 상태인지 여부"
                  },
                  "empty" : {
                    "type" : "boolean",
                    "description" : "정렬 정보가 비어있는지 여부"
                  }
                }
              }
            }
          },
          "sort" : {
            "type" : "object",
            "properties" : {
              "unsorted" : {
                "type" : "boolean",
                "description" : "정렬되지 않은 상태인지 여부"
              },
              "sorted" : {
                "type" : "boolean",
                "description" : "정렬된 상태인지 여부"
              },
              "empty" : {
                "type" : "boolean",
                "description" : "정렬 정보가 비어있는지 여부"
              }
            }
          },
          "first" : {
            "type" : "boolean",
            "description" : "첫번째 페이지 여부"
          },
          "content" : {
            "type" : "array",
            "description" : "펀딩 목록",
            "items" : {
              "type" : "object",
              "properties" : {
                "endDate" : {
                  "type" : "array",
                  "description" : "펀딩 종료일",
                  "items" : {
                    "oneOf" : [ {
                      "type" : "object"
                    }, {
                      "type" : "boolean"
                    }, {
                      "type" : "string"
                    }, {
                      "type" : "number"
                    } ]
                  }
                },
                "state" : {
                  "type" : "string",
                  "description" : "펀딩 상태"
                },
                "id" : {
                  "type" : "number",
                  "description" : "펀딩 ID"
                },
                "category" : {
                  "type" : "string",
                  "description" : "펀딩 카테고리"
                },
                "title" : {
                  "type" : "string",
                  "description" : "펀딩 제목"
                },
                "startDate" : {
                  "type" : "array",
                  "description" : "펀딩 시작일",
                  "items" : {
                    "oneOf" : [ {
                      "type" : "object"
                    }, {
                      "type" : "boolean"
                    }, {
                      "type" : "string"
                    }, {
                      "type" : "number"
                    } ]
                  }
                }
              }
            }
          },
          "empty" : {
            "type" : "boolean",
            "description" : "페이지가 비어있는지 여부"
          },
          "totalElements" : {
            "type" : "number",
            "description" : "총 요소 수"
          }
        }
      },
      "members-signup1017061083" : {
        "type" : "object",
        "properties" : {
          "zipCode" : {
            "type" : "string",
            "description" : "우편번호"
          },
          "password" : {
            "type" : "string",
            "description" : "비밀번호"
          },
          "address" : {
            "type" : "string",
            "description" : "주소"
          },
          "name" : {
            "type" : "string",
            "description" : "이름(닉네임)"
          },
          "detailAddress" : {
            "type" : "string",
            "description" : "상세주소"
          },
          "tel" : {
            "type" : "string",
            "description" : "전화번호"
          },
          "email" : {
            "type" : "string",
            "description" : "이메일"
          }
        }
      },
      "auth-login851787074" : {
        "type" : "object",
        "properties" : {
          "token" : {
            "type" : "string",
            "description" : "JWT 토큰"
          }
        }
      },
      "members-duplicate-name-1386254058" : {
        "type" : "object",
        "properties" : {
          "result" : {
            "type" : "boolean",
            "description" : "중복 여부 ( true: 중복, false: 중복 아님 )"
          },
          "code" : {
            "type" : "string",
            "description" : "코드"
          },
          "message" : {
            "type" : "string",
            "description" : "메시지"
          }
        }
      },
      "fundings-manage-image-upload204345988" : {
        "type" : "object",
        "properties" : {
          "name" : {
            "type" : "string",
            "description" : "펀딩 이미지 파일명"
          },
          "link" : {
            "type" : "string",
            "description" : "펀딩 이미지 링크"
          },
          "uploadedAt" : {
            "type" : "array",
            "description" : "펀딩 이미지 업로드 일시",
            "items" : {
              "oneOf" : [ {
                "type" : "object"
              }, {
                "type" : "boolean"
              }, {
                "type" : "string"
              }, {
                "type" : "number"
              } ]
            }
          },
          "id" : {
            "type" : "number",
            "description" : "펀딩 이미지 ID"
          }
        }
      },
      "orders-guest933516009" : {
        "type" : "object",
        "properties" : {
          "zipCode" : {
            "type" : "string",
            "description" : "우편 번호"
          },
          "address" : {
            "type" : "string",
            "description" : "주소"
          },
          "fundingId" : {
            "type" : "number",
            "description" : "펀딩 ID"
          },
          "phone" : {
            "type" : "string",
            "description" : "전화번호"
          },
          "payMethod" : {
            "type" : "string",
            "description" : "결제 방법"
          },
          "nameOpen" : {
            "type" : "boolean",
            "description" : "이름 공개 여부"
          },
          "recipient" : {
            "type" : "string",
            "description" : "수령인"
          },
          "detailAddress" : {
            "type" : "string",
            "description" : "상세 주소"
          },
          "subPhone" : {
            "type" : "string",
            "description" : "보조 전화번호"
          },
          "fundingItems" : {
            "type" : "array",
            "description" : "펀딩 아이템",
            "items" : {
              "type" : "object",
              "properties" : {
                "quantity" : {
                  "type" : "number",
                  "description" : "펀딩 아이템 구매 수량"
                },
                "fundingItemId" : {
                  "type" : "number",
                  "description" : "펀딩 아이템 ID"
                }
              }
            }
          },
          "amountOpen" : {
            "type" : "boolean",
            "description" : "금액 공개 여부"
          }
        }
      },
      "orders-member-1397712321" : {
        "type" : "object",
        "properties" : {
          "zipCode" : {
            "type" : "string",
            "description" : "우편 번호"
          },
          "address" : {
            "type" : "string",
            "description" : "주소"
          },
          "fundingId" : {
            "type" : "number",
            "description" : "펀딩 ID"
          },
          "phone" : {
            "type" : "string",
            "description" : "전화번호"
          },
          "payMethod" : {
            "type" : "string",
            "description" : "결제 방법"
          },
          "nameOpen" : {
            "type" : "boolean",
            "description" : "이름 공개 여부"
          },
          "recipient" : {
            "type" : "string",
            "description" : "수령인"
          },
          "detailAddress" : {
            "type" : "string",
            "description" : "상세 주소"
          },
          "subPhone" : {
            "type" : "string",
            "description" : "보조 전화번호"
          },
          "fundingItems" : {
            "type" : "array",
            "description" : "펀딩 아이템",
            "items" : {
              "type" : "object",
              "properties" : {
                "quantity" : {
                  "type" : "number",
                  "description" : "펀딩 아이템 구매 수량"
                },
                "fundingItemId" : {
                  "type" : "number",
                  "description" : "펀딩 아이템 ID"
                }
              }
            }
          },
          "amountOpen" : {
            "type" : "boolean",
            "description" : "금액 공개 여부"
          },
          "memberId" : {
            "type" : "number",
            "description" : "회원 ID (비회원 주문시 NULL)"
          }
        }
      },
      "fundings-manage-create2003668828" : {
        "type" : "object",
        "properties" : {
          "targetDonation" : {
            "type" : "number",
            "description" : "목표 모금액"
          },
          "endDate" : {
            "type" : "string",
            "description" : "펀딩 종료일"
          },
          "miniTitle" : {
            "type" : "string",
            "description" : "펀딩 소제목"
          },
          "managerId" : {
            "type" : "number",
            "description" : "펀딩 관리자 ID"
          },
          "category" : {
            "type" : "string",
            "description" : "펀딩 카테고리"
          },
          "title" : {
            "type" : "string",
            "description" : "펀딩 제목"
          },
          "startDate" : {
            "type" : "string",
            "description" : "펀딩 시작일"
          },
          "content" : {
            "type" : "string",
            "description" : "펀딩 내용"
          }
        }
      },
      "members-withdrawal-455553110" : {
        "type" : "object",
        "properties" : {
          "result" : {
            "type" : "boolean",
            "description" : "성공 여부"
          },
          "code" : {
            "type" : "string",
            "description" : "코드"
          },
          "message" : {
            "type" : "string",
            "description" : "메시지"
          }
        }
      },
      "members-signup-779979142" : {
        "type" : "object",
        "properties" : {
          "name" : {
            "type" : "string",
            "description" : "이름(닉네임)"
          },
          "id" : {
            "type" : "number",
            "description" : "회원번호"
          },
          "email" : {
            "type" : "string",
            "description" : "이메일"
          }
        }
      },
      "auth-login1294563024" : {
        "type" : "object",
        "properties" : {
          "password" : {
            "type" : "string",
            "description" : "로그인할 비밀번호"
          },
          "email" : {
            "type" : "string",
            "description" : "로그인할 이메일"
          }
        }
      },
      "fundings-id1140910618" : {
        "type" : "object",
        "properties" : {
          "targetDonation" : {
            "type" : "number",
            "description" : "목표 금액"
          },
          "endDate" : {
            "type" : "array",
            "description" : "펀딩 종료일",
            "items" : {
              "oneOf" : [ {
                "type" : "object"
              }, {
                "type" : "boolean"
              }, {
                "type" : "string"
              }, {
                "type" : "number"
              } ]
            }
          },
          "miniTitle" : {
            "type" : "string",
            "description" : "펀딩 부제목"
          },
          "managerId" : {
            "type" : "number",
            "description" : "펀딩 관리자 아이디"
          },
          "title" : {
            "type" : "string",
            "description" : "펀딩 제목"
          },
          "managerName" : {
            "type" : "string",
            "description" : "펀딩 관리자 이름"
          },
          "content" : {
            "type" : "string",
            "description" : "펀딩 내용"
          },
          "managerEmail" : {
            "type" : "string",
            "description" : "펀딩 관리자 이메일"
          },
          "phoneNumber" : {
            "type" : "string",
            "description" : "펀딩 관리자 전화번호"
          },
          "state" : {
            "type" : "string",
            "description" : "펀딩 상태"
          },
          "id" : {
            "type" : "number",
            "description" : "펀딩 아이디"
          },
          "category" : {
            "type" : "string",
            "description" : "펀딩 카테고리"
          },
          "successDonation" : {
            "type" : "number",
            "description" : "달성 금액"
          },
          "startDate" : {
            "type" : "array",
            "description" : "펀딩 시작일",
            "items" : {
              "oneOf" : [ {
                "type" : "object"
              }, {
                "type" : "boolean"
              }, {
                "type" : "string"
              }, {
                "type" : "number"
              } ]
            }
          }
        }
      },
      "fundings-manage-create39299276" : {
        "type" : "object",
        "properties" : {
          "registeredAt" : {
            "type" : "array",
            "description" : "펀딩 등록일",
            "items" : {
              "oneOf" : [ {
                "type" : "object"
              }, {
                "type" : "boolean"
              }, {
                "type" : "string"
              }, {
                "type" : "number"
              } ]
            }
          },
          "id" : {
            "type" : "number",
            "description" : "펀딩 ID"
          }
        }
      },
      "orders-member1541140646" : {
        "type" : "object",
        "properties" : {
          "fundingId" : {
            "type" : "number",
            "description" : "펀딩 ID"
          },
          "orderNumber" : {
            "type" : "string",
            "description" : "주문 번호"
          },
          "isNameOpen" : {
            "type" : "boolean",
            "description" : "이름 공개 여부"
          },
          "orderId" : {
            "type" : "number",
            "description" : "주문 ID"
          },
          "isAmountOpen" : {
            "type" : "boolean",
            "description" : "금액 공개 여부"
          },
          "orderedRewards" : {
            "type" : "array",
            "description" : "주문한 리워드 제목들",
            "items" : {
              "oneOf" : [ {
                "type" : "object"
              }, {
                "type" : "boolean"
              }, {
                "type" : "string"
              }, {
                "type" : "number"
              } ]
            }
          },
          "orderStatus" : {
            "type" : "string",
            "description" : "주문 상태"
          },
          "recipientInfo" : {
            "type" : "object",
            "properties" : {
              "address" : {
                "type" : "object",
                "properties" : {
                  "zipCode" : {
                    "type" : "string",
                    "description" : "수령인 우편번호"
                  },
                  "addressInfo" : {
                    "type" : "string",
                    "description" : "수령인 기본 주소"
                  },
                  "detailAddress" : {
                    "type" : "string",
                    "description" : "수령인 상세 주소"
                  }
                },
                "description" : "수령인 주소 정보"
              },
              "phone" : {
                "type" : "string",
                "description" : "수령인 전화번호"
              },
              "name" : {
                "type" : "string",
                "description" : "수령인 이름"
              },
              "subPhone" : {
                "type" : "string",
                "description" : "수령인 보조 전화번호"
              }
            },
            "description" : "수령인 정보"
          }
        }
      }
    },
    "securitySchemes" : {
      "APIKey" : {
        "type" : "apiKey",
        "name" : "Authorization",
        "in" : "header"
      }
    }
  },
  "security" : [ {
    "APIKey" : [ ]
  } ]
}