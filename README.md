# 자바 안드로이드 키오스크 프로젝트
## 2조
-- 프로젝트 이름 : 키오스크

-- 기간 : 12/23 ~ 1/07 

-- 팀원 : 🙍‍♂️엄정현, 🙍‍♂️김지욱, 🙍‍♂️[백건우](https://github.com/gunwoo100/ProJect2), 🙍‍♀️이새로미

-- ☕ 담당역할 : 포인트 적립 및 코드 통합

-- ⏱ 제작기간 : 2~3주

***

# • 1. 주문 내역 확인 페이지
<div align="center">
  <img src="https://github.com/user-attachments/assets/fd2e0166-8c40-4774-bf15-793999d459ff" alt="주문 내역 확인 페이지" width="300">
</div>

- 포인트 적립 버튼을 추가하고, 해당 버튼을 누르면 포인트 적립 Dialog 가 뜨게 됩니다.

- CheckOrderActivity 에서 클릭 이벤트를 처리하는 코드입니다.
  - Dialog 내에서 이루어지는 클릭 이벤트를 Controller 에 연결시켜줍니다.
  - 포인트를 사용하면 할인된 가격을 계산합니다.
```java
public void onClickedInPointsDialog(View view) {
  pointDialogController.onClick(view);

  if (GlobalVariable.getRedeemedPoints() != 0) setDiscountPrice();
}

public void onClickPointsRedeem(View view) {
  pointDialogController = new PointDialogController(this);
}
```
