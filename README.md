# 자바 안드로이드 키오스크 프로젝트
## 2조
-- 프로젝트 이름 : 키오스크

-- 기간 : 12/23 ~ 1/07 

-- 팀원 : 🙍‍♂️엄정현, 🙍‍♂️[김지욱](https://github.com/orangetunnelkim/myAndorid) : 쿠폰 할인, 🙍‍♂️[백건우](https://github.com/gunwoo100/ProJect2) : 메뉴 선택, 🙍‍♀️이새로미 : 주문 내역 확인

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

***

# • 2. 포인트 Dialog 컨트롤러
- states 변수에 상태 1, 2, 3 을 넣어서 상태를 관리합니다. 
```java
public class PointDialogController implements PointsDialogCompleteListener {
    // 다이얼로그 상태를 저장할 변수
    private static int states;

    // 포인트 다이얼로그의 상태값을 정의
    public final int EARN_POINTS = 1; // 포인트 적립 다이얼로그
    public final int CHECK_POINTS = 2; // 포인트 확인 다이얼로그
    public final int REDEEM_POINTS = 3; // 포인트 사용 다이얼로그

    // 다이얼로그 객체들
    private EarnPointsFragment d1;
    private CheckPointsFragment d2;
    private RedeemPointsFragment d3;
```
- 생성자에서 핸드폰 번호를 체크하여 상태를 제어하고, 결제 가격에 따라서 포인트를 적립합니다.
```java
    // 포인트 적립 비율 %
    public final float EARN_RATE = 5.0f;   

    // FragmentManager와 Context, 결제 정보들
    private FragmentManager manager;
    private Context context;

    public PointDialogController(Context context) {
        this.context = context;

        if (GlobalVariable.isPhoneNumberSet() && UserData.getIndexByPhone(GlobalVariable.getPhoneNumberAdded010()) != -1) {
            states = REDEEM_POINTS;
        } else {
            states = EARN_POINTS;
        }

        // FragmentManager 가져오기
        manager = ((AppCompatActivity) context).getSupportFragmentManager();

        int totalPrice = GlobalVariable.getTotalPrice();
        GlobalVariable.setPointsToBeEarned((int)(totalPrice * 0.01 * EARN_RATE));

        showDialog(); // 다이얼로그 보여주기
    }
```
```java
    // 상태에 맞는 다이얼로그를 보여주는 메소드
    public void showDialog() {
        switch (states) {
            case EARN_POINTS:
                showEarnPointsFragment(); break;
            case CHECK_POINTS:
                showCheckPointsFragment(); break;
            case REDEEM_POINTS:
                showRedeemPointsFragment(); break;
            default: break;
        }
    }

    // 다이얼로그 완료시 동작하는 메소드
    @Override
    public void onDialogComplete(String dialogTag) {
        switch (dialogTag) {
            case "EarnPoints":
                onEarnPointsDialogComplete(); break;
            case "CheckPoints":
                onCheckPointsDialogComplete(); break;
            case "RedeemPoints":
                onRedeemPointsDialogComplete(); break;
            default:
                break;
        }
    }
```
- 포인트 Dialog 완료 리스너 입니다.
  - Dialog 의 동작이 완료 되었을 때 컨트롤러에서 메소드를 작동시키기 위함입니다.
```java
public interface PointsDialogCompleteListener {
    void onDialogComplete(String dialogTag);
}
```

## 문제와 해결
처음에는 각각의 Dialog 에서 각각의 변수를 설정하여 다시 컨트롤러로 돌려보내는 방식으로 구현하려 했으나, 컨트롤러 내에서 변수 이름 설정, 컨트롤러 밖에서 값 사용 등 매우 복잡하여 후에 코드를 합칠 때 매우 어려움을 느꼈습니다.
그래서 ***GlobalVariable*** 클래스에서 전역적으로 쓰기 위한 변수들을 설정해두고 쓰는 방식으로 변경하였습니다.

***

# • 3. 포인트 적립 위한 번호 입력 Dialog
<div align="center">
  <img src="https://github.com/user-attachments/assets/51cad7a2-ede7-4016-9a36-9bf8117fc5db" alt="포인트 적립 번호 입력 Dialog" width="300">
</div>

- 생성자에서 설정한 적립 예정 포인트를 보여줍니다.
- 010 은 미리 입력되어 있으며, 나머지 번호 8자를 입력 받습니다.

- Dialog 를 생성하는 코드 일부입니다. 위에서의 완료 리스너를 추가하여 8자를 입력 받으면 호출합니다.
```java
// 포인트 적립 (번호 입력) 다이얼로그를 구현한 클래스
public class EarnPointsFragment extends DialogFragment {

    private PointsDialogCompleteListener listener; // 다이얼로그 완료 리스너

    // 리스너 설정 메서드
    public void setPointsDialogCompleteListener(PointsDialogCompleteListener listener) {
        this.listener = listener;
    }

    private Dialog dialog; // 다이얼로그 객체
    private TextView text1, text2; // 전화번호 표시를 위한 텍스트뷰

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 다이얼로그 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.points_earn); // 레이아웃 설정
        dialog = builder.create(); // 다이얼로그 생성
        dialog.setCanceledOnTouchOutside(false); // 다이얼로그 외부 터치로 종료 방지

        dialog.setOnShowListener(d -> {
            // 숫자 패드 뷰를 동적으로 추가
            FrameLayout frame = dialog.findViewById(R.id.frame_number_pad);
            NumberPadView numberPadView = new NumberPadView(getActivity());
            frame.addView(numberPadView);

            // 예상 적립 포인트 표시
            TextView textView = dialog.findViewById(R.id.text_expected_earn_points);
            textView.setText(GlobalVariable.getPointsToBeEarned() + "P");

            // 숫자 패드 클릭 리스너 설정
            numberPadView.setOnNumberPadClickListener(new NumberPadView.OnNumberPadClickListener() {
                @Override
                public void onNumberClicked(String number) {
                    // 입력 길이가 8자를 초과하면 잘라냄
                    if (number.length() > 8)
                        numberPadView.setInput(numberPadView.getInput().substring(0, 8));

                    // 입력된 전화번호를 업데이트
                    String phoneNumber = numberPadView.getInput();
                    setTextPhoneNumber(phoneNumber);

                    // 전화번호가 8자리에 도달했을 때 완료 리스너 호출
                    if (phoneNumber.length() == 8 && listener != null) {
                        GlobalVariable.setPhoneNumber(phoneNumber);
                        listener.onDialogComplete("EarnPoints");
                    }
                }
            });
        });

        return dialog; // 생성된 다이얼로그 반환
    }
```

## 문제와 해결
3, 4, 5 번에 있는 Dialog 에서 NumberPadView 를 추가하거나 글씨에 효과를 주기 위한 SpannableString 를 사용하고 있으며, 추가적으로 변수값을 적용하기 위해 값을 불러온 뒤 TextView 에 값을 설정하고 있습니다.
이 때 onCreateDialog 에서 findViewById 를 사용했을 때 NullPointerException 이 발생하였습니다. 그래서 ***setOnShowListener*** 를 사용하여 Dialog가 화면에 완전히 표시된 후 필요한 View 작업을 수행하도록 변경했습니다.

***

# • 4. 신규 고객 확인 Dialog
<div align="center">
  <img src="https://github.com/user-attachments/assets/c8ed419d-a5fb-414c-91b4-77ead4e083d4" alt="신규 고객 확인 Dialog" width="300">
</div>

- 3.Dialog 에서 입력 받은 핸드폰 번호가 신규 고객일 경우 띄우는 Dialog 입니다. 닫기를 누르면 다시 3.Dialog 로 이동하며, 확인을 누를 경우 5.Dialog 로 이동하고 유저리스트에 추가가 됩니다.
- 컨트롤러에 있는 3.Dialog 완료 메소드 코드 입니다.

```java
    // 포인트 적립 다이얼로그 완료 후 처리
    private void onEarnPointsDialogComplete() {
        String phoneNumber = GlobalVariable.getPhoneNumberAdded010();
        // 전화번호로 사용자 정보 찾기
        UserData userData = UserData.searchUserByPhone(phoneNumber);

        // 신규 고객인지 아닌지 체크
        if (userData == null) {
            states = CHECK_POINTS;
        } else {
            GlobalVariable.setUserData(userData);
            states = REDEEM_POINTS;
            d1.dismiss();
        }

        showDialog();
    }
```

## 문제와 해결
3번 Dialog 에서 4번 Dialog 로 넘어올 때, 8개의 문자를 입력하고 그것이 신규 고객일 때 넘어올 수 있도록 설계하였습니다. 그러나 이 때 8번째 문자를 입력할 때 숫자 버튼을 빠르게 누르게 되면 
4번 Dialog 가 여러번 뜨는 현상이 발생하였습니다. 이 때, isAdded 와 isVisible 메소드도 사용해보았지만 해당 문제가 해결되지 않았습니다. 
그래서 ***isDialogOpen*** 라는 상태 변수를 static 으로 선언하여 컨트롤러에서 조건문으로 체크하여 열 수 있도록 하였습니다.

***

# • 5. 적립 포인트 사용 Dialog
<div align="center">
  <img src="https://github.com/user-attachments/assets/e85e9390-c5cd-410d-be2c-011cd8e7da57" alt="적립 포인트 사용 Dialog" width="300">
</div>

- 3, 4. Dialog 를 거치면 포인트 사용 Dialog 가 뜹니다. 신규 고객일 경우 포인트가 없기 때문에 넘버 패드나 전체 사용 버튼을 눌러도 입력되지 않습니다.

<div align="center">
  <img src="https://github.com/user-attachments/assets/1d9797a0-c650-4817-8592-e011251c0d97" alt="적립 포인트 사용 Dialog 3" width="300">
  <img src="https://github.com/user-attachments/assets/886d11cf-a7a6-429a-a672-a46ded7cbb1c" alt="적립 포인트 사용 Dialog 4" width="300">
</div>

- 포인트가 있는 유저일 경우 최소 금액 이상 입력 되어야 파란 글씨로 바뀌고 확인 버튼도 활성화 됩니다. 그리고 확인 버튼을 누르면 주문 내역 확인 페이지로 돌아가서 할인 금액이 재설정 됩니다.

***

# • 6. 시연
<div align="center">
  <img src="https://github.com/user-attachments/assets/9fc064bb-f5fb-437f-bb5b-30b484afdc5e" alt="app 시연" width="300">
</div>

## 느낀점
파트 분배함에 있어서 주어진 파트들이 크게 네 부분이 있어서 그 부분을 하나씩 맡아서 되었습니다. 이는 각자 해볼 수 있다는 장점이 있었지만, 같이 한다는 부분에 있어서는 아쉬움이 있었습니다.
그리고 앱을 만드는 데에 있어서 작동시켰던 제 핸드폰을 기준으로만 만들다보니 다른 디바이스에서 View 부분들이 원하는 대로 보이지 않게 되었습니다. 만드는 데에 있어 그런 환경에 대해 더 고려해야함을 느꼈습니다.
