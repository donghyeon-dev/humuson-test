## 참고사항
1. `HttpOrderClient`의 dummy uri는 오직 하나의 유형의 Order객체만 반환합니다.
2. `OrderStorageClient`의 uri는 작동하지 않습니다.
3. Mac환경에서 Embedded Redis를 사용하여 Windows환경에서는 동작하지 않을 수 있습니다. 

# 1. 개발환경
- MacOS Sequoia
- SpringBoot 3.2.1
- Spring Data Redis
- Embedded Redis
- lombok
- openFeign

# 2. 프로세스
### 주문데이터 가져오기
1. 주문데이터를 가져오기 위해 `GET /order` 요청을 합니다.
2. `HttpOrderClient.fetchOrderData()`를 통해 외부 시스템에서 주문데이터를 가져옵니다.
3. 가져온 주문데이터를 Redis에 저장합니다. `OrderRepository.save()`
4. 외부 시스템으로 주문데이터를 전송한다. `ExtenralOrderStorageService.sendOrderData()`

### 메모리에 저장된 특정 주문데이터 조회
1. 특정 주문ID를 통해 주문데이터를 조회합니다. `GET /order/{orderId}`
2. Redis에서 주문ID를 통해 주문정보를 조회합니다. `OrderRepository.findById()`
3. 조회된 주문데이터를 반환합니다.

### 메모리에 저장된 주문데이터 리스트 조회
1. 저장된 주문데이터 Redis에서 전부 꺼내어 리스트를 반환합니다. `GET /orders`
2. Redis에 저장된 주문데이터를 전부 조회합니다. `OrderRepository.findAll()`
3. 조회된 주문데이터 리스트를 반환합니다.


# 3. 요구사항에 대한 설명
### 주문 관리 시스템
1. 외부 시스템으로부터 주문데이터를 가져와 시스템에 저장하는 기능
<Br>=>`openFeign`을 사용하여 외부 시스템으로부터 주문데이터를 가져옵니다. `HttpOrderClient.fetchOrderData()` 
2. 주문데이터
```java
 public class Order {
    private String orderId;
    private String customerName;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
}
```
주문상태는 enum을 사옹하여 처리중, 배송중, 배송완료, 취소됨 으로 구분합니다.

### 외부 시스템과의 데이터 연동
1. feignclient를 사용하여 외부 시스템과 통신합니다. 
2. 주문데이터를 외부 시스템으로 전송합니다. `HttpOrderClient.sendOrderData()`

### 데이터 저장 및 조회
1. 받아온 데이터는 Redis에 저장합니다. `OrderRepository.save()`
2. Redis에서 주문ID를 통해 주문정보를 조회합니다. `OrderRepository.findById()`
3. 저장된 주문데이터 Redis에서 전부 꺼내어 리스트를 반환합니다. `GET /orders`

### 데이터 연동 인터페이스 설계
1. 외부 시스템과의 통신을 위한 인터페이스를 설계합니다. `HttpOrderClient`
2. 통신과 데이터 변환은 feign에 의해 처리되며, 별도 혹은 추가로 처리해야할 부분은 `FeignConfig`에 정의할 수 있습니다.

### 예외처리
1. 외부 시스템과의 통신이 실패할 경우, 에러를 처리합니다. (`FeignErrorDecoder`)
2. 내부적으로 CustomException을 사용해 Exception의 가독성을 높입니다.

### 확장성 고려
1. `HttpOrderCleient`, `OrderStorageClient와` 같이 외부 시스템과의 통신을 위한 인터페이스를 정의하여 확장성을 고려합니다.
2. `ExternalOrderService`는 인터페이스로 `ExternalOrderStorageService` 와 `HttpExternalOrderService`를 구현하여 확장성을 고려합니다.


# 4. 외부시스템과 연동을 담당하는 클래스 다이어그램
![image](https://github.com/user-attachments/assets/76ce5a05-8de8-44e8-ab85-90ae98d03edf)