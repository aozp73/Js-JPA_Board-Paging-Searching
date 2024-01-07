function cancel() {
    if (confirm("작성 중인 글이 모두 지워집니다.")) {
        location.href = '/board/list';
    } else {
    }
}