function sendAjax(url, method, data, successCallback, errorCallback) {
    var xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.onload = function() {
        if (xhr.status === 200) {
            successCallback(xhr.responseText);
        }
        else {
            errorCallback(xhr.statusText);
        }
    };
    xhr.onerror = function() {
        errorCallback('请求出错');
    };
    if (method.toUpperCase() === 'POST') {
        xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        xhr.send(JSON.stringify(data));
    }
    else {
        xhr.send();
    }
}
function submitForm() {
    var form = document.getElementById('myForm');
    var formData = new FormData(form);

    sendAjax('http://localhost:8080/ssmjjbm/sendMail', 'POST', formData, function(responseText) {
        console.log('成功发送数据：' + responseText);
    }, function(errorMessage) {
        console.log('发送数据失败：' + errorMessage);
    });
}