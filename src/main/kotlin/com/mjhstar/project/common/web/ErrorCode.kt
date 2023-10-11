package com.mjhstar.project.common.web;

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    EXIST_USER_ID(HttpStatus.OK, "USER-1", "이미 등록된 ID입니다."),
    NOT_EXIST_USER(HttpStatus.OK, "USER-2", "회원 정보가 존재하지 않습니다."),
    INVALID_USER_NAME(HttpStatus.OK, "USER-3", "이름을 입력해주세요."),
    INVALID_USER_ID(HttpStatus.OK, "USER-4", "ID를 입력해주세요."),


    IS_MORE_THAN_HAS_POINT(HttpStatus.OK, "POINT-1", "보유한 적립금보다 많은 적립금을 사용할 수 없습니다."),
    FAIL_USE_POINT(HttpStatus.OK, "POINT-2", "적립금 사용에 실패하였습니다."),
    FAIL_CHARGE_POINT(HttpStatus.OK, "POINT-3", "포인트 적립에 실패하였습니다."),
    FAIL_CHARGE_POINT_ZERO(HttpStatus.OK, "POINT-4", "포인트 적립은 0원 이상만 가능합니다."),
    FAIL_USE_POINT_ZERO(HttpStatus.OK, "POINT-5", "포인트 사용은 0원 이상만 가능합니다."),
    FAIL_INVALID_POINT(HttpStatus.OK, "POINT-6","포인트를 사용할 수 없습니다."),

    NOT_EXIST_TX(HttpStatus.OK, "TX-1", "포인트 사용 이력이 존재하지 않습니다."),
    FAIL_CANCEL_TX(HttpStatus.OK, "TX-2", "포인트 사용 취소에 실패하였습니다."),

    FAIL_CONVERT_DATE(HttpStatus.OK,"COM-1", "올바르지 않은 날짜 형식입니다."),
    TOO_LARGE_PAGE_SIZE(HttpStatus.OK,"COM-2", "조회할 수 있는 최대 페이지 사이즈는 100입니다."),
    FAIL_INQUIRY_DATE(HttpStatus.OK,"COM-3", "조회할 수 있는 최대 기간은 6개월입니다."),


    ;
}
