<html>
    <head>
        <meta charset="UTF-8">
        <title>Create</title>
    </head>
    <body>
        <p><textarea id="code_snippet">Write your code here...</textarea></p>
        <p>Time Restrictions:<input id="time_restriction" type="text"/></p>
        View Restrictions:<input id="views_restriction" type="text"/>
        <button id="send_snippet" type="submit" onclick="send()">Submit</button>
        <script>
            function send() {
                let object = {
                    "code": document.getElementById("code_snippet").value,
                    "time": document.getElementById("time_restriction").value,
                    "views": document.getElementById("views_restriction").value
                };

                let json = JSON.stringify(object);

                let xhr = new XMLHttpRequest();
                xhr.open("POST", '/api/code/new', false)
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.send(json);

                if (xhr.status == 200) {
                    alert("Success!");
                }
            }
        </script>
    </body>
</html>