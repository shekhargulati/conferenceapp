var wsUrl;
if (window.location.protocol == 'http:') {
          wsUrl = 'ws://' + window.location.host + ':8000/quiz';
} else {
          wsUrl = 'wss://' + window.location.host + ':8443/quiz';
}
console.log('WebSockets Url : ' + wsUrl);
var ws = new WebSocket(wsUrl);

ws.onopen = function(event){
        console.log('WebSocket connection started');
};

ws.onclose = function(event){
         console.log("Remote host closed or refused WebSocket connection");
     console.log(event);
};

ws.onmessage = function(event){
    console.log(JSON.parse(event.data));
    var data = JSON.parse(event.data);
    $("#questionText").text(data.question);
    $("#message").text(data.message);
    var style = data.style;
    if(style === "info"){
    	$("#message").removeClass("label label-success label-danger label-primary label-info").addClass("label label-info");
    }else if(style === "success"){
    	$("#message").removeClass("label label-success label-danger label-primary label-info").addClass("label label-success");
    }else{
    	$("#message").removeClass("label label-success label-danger label-primary label-info").addClass("label label-danger");
    }
};

$("button#start").on('click',function(){
    ws.send('start');
    $("#answerBox").show();
    $("button#start").hide()
});

$("button#next").on('click',function(){
    var message = $('#answer').val();
    console.log('message .. '+message);
    $("#answer").val("");
    ws.send(message);
});

$("button#end").on('click',function(){
    ws.send('end');
    $("#wrap").html("<h2>Game Over</h2>")
});