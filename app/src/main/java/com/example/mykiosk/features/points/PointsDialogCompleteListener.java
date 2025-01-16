package com.example.mykiosk.features.points;

// 포인트 다이얼로그 완료 리스너 인터페이스
public interface PointsDialogCompleteListener {
    // 다이얼로그 작업 완료 시 호출되는 메서드
    // dialogTag: 완료된 다이얼로그의 태그를 전달
    void onDialogComplete(String dialogTag);
}
