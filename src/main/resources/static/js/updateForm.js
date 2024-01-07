function updateBoard(boardId) {
    let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;

    let data = {
        id: boardId,
        title: title,
        content: content
    }

    if (confirm("게시글을 수정하시겠습니까?")) {
        $.ajax({
            url: '/auth/board',
            type: 'PUT',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),

            success: function(response) {
                console.log(response);
                location.href = '/board/' + boardId;
            },
            error: function(error) {
                console.log(error);
                alert(error.responseJSON.data);
                location.href = '/board/' + boardId;
            }
        });
    } else {
    }
}