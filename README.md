# 프로젝트

# 개발 필수요건
- API에서 회원정보는 '회원번호(회원에게 부여된 유니크한 번호)' 이외의 다른 정보는 전달받지 않음
- 각 API 에서 ‘회원번호’ 이외에 request, response는 자유롭게 구성
- 회원 별 적립금 합계는 마이너스가 될 수 없음
- 개발해야 할 API는 다음과 같습니다. 
  - 회원별 적립금 합계 조회 
  - 회원별 적립금 적립/사용 내역 조회 
    - 페이징 처리 
  - 회원별 적립금 적립 
  - 회원별 적립금 사용 
    - 적립금 사용시 우선순위는 먼저 적립된 순서로 사용(FIFO)
  - ORM 사용 (ex: JPA / typeorm 등)

# 개발 추가요건
- 적립금의 유효기간 구현(1년)
- 회원별 적립금 사용취소 API 개발
  - 적립금 사용 API 호출하는 쪽에서 Rollback 처리를 위한 용도
- 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 개발
- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 개발

# Table

- 회원 테이블
  - 회원 index (unique)
  - 회원 이름
  - 데이터 생성 시간
- 적립금 테이블
  - 데이터 idx
  - 사용자 idx
  - 현재 보유중인 적립금
  - 데이터 업데이트 시간
- 적립금 적립 테이블
  - 데이터 idx
  - 회원 idx
  - 적립금 트랜잭션 idx
  - 상태 (사용 / 활성화 / 만료)
  - 적립 포인트
  - 남은 포인트
  - 적립금 만료 시간
  - 데이터 생성 시간
  - 데이터 업데이트 시간
- 적립금 트랜잭션 테이블
  - 데이터 idx
  - 회원 idx
  - 상태 (충전 / 사용 / 사용취소)
  - 트랜잭션에 사용된 포인트
  - 데이터 생선 시간
  - 포인트 사용 시간
  - 포인트 사용 취소 시간
- 포인트 사용 내역 테이블
  - 데이터 idx
  - 포인트 적립 데이터 idx
  - 포인트 트랜잭션 데이터 idx
  - 사용 포인트
  - 데이터 상태(사용 / 사용취소)
  - 데이터 생성 시간
  - 데이터 업데이트 시간

# API 및 기능 설명
- 유저 생성
  - POST
  - /{apiVersion}/user
  - body : { "userId":"id", "userName":"이름" }
- 유저 조회
  - GET
  - /{apiVersion}/user
  - param : userId
- 적립금 조회
  - GET
  - /{apiVersion}/point
  - param : userIdx
- 적립금 충전
  - POST
  - /{apiVersion}/charge
  - body : { "userIdx": 0, "chargePoints" : 100 }
- 적립금 사용
  - POST
  - /{apiVersion}/use
  - body : { "userIdx": 0, "usePoints" : 100 }
- 적립금 사용 취소
  - PUT
  - /{apiVersion}/cancel
  - body : { "userIdx": 0, "txIdx" : 0 }
- 적립금 사용 / 취소 / 충전 이력 조회
  - GET
  - /{apiVersion}/history
  - param : userIdx, type, from, to, size, page

- PointChargeExpirationScheduler
  - 매일 0시0분에 스케줄러를 시작해서, 만료된 포인트 처리
  - 만료처리해야하는 데이터의 Id를 찾아오고, batchSize만큼 잘라서 비동기로 처리