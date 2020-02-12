<!DOCTYPE html>
<html>

<head>
    <title>Post | Microblog</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</head>

<body>
    <div>
        <div class="jumbotron-fluid" id="headerBox">
            <div>
                <p id="header"> &nbsp;&nbsp;&nbsp;&nbsp;Microblog </p>
            </div>
        </div>
        <div class="container" id="registrationContainer">

            <form action="/Microblog/AddPostServlet" method="post">
                <button type="submit" class="btn btn-primary">Aggiungi un post</button> <br> <br>
            </form>
            <%@ page import="entity.*" %>
            <%@ page import="DAO.*" %>
            <%@ page import="java.util.List" %>
            <% List<Post> postList = (List<Post>) request.getAttribute("postList"); %>
            <% for (int i = 0; i < postList.size(); i++) { %>
            <div class="card">
                <% Post p = postList.get(i); %>
                <h5 class="card-header">
                    <% out.println("Post di " + p.getUtente().getUsername() + " - " + p.getDataOra().toString()); %>
                </h5>
                <div class="card-body">
                    <h5 class="card-title"><% out.println(p.getTitolo()); %></h5>
                    <p class="card-text"><% out.println(p.getContenuto()); %></p>
                </div>
                <ul class="list-group list-group-flush">
                    <% List<Commento> commentoList = (List<Commento>) CommentoDao.findCommentoByPost(p); %>
                    <% for (int j = 0; j < commentoList.size(); j++) { %>
                    <% Commento c = commentoList.get(j); %>
                    <li class="list-group-item"><b>
                            <% out.println(c.getUtente().getUsername() + " - " + c.getDataOra().toString()); %></b>
                        <% out.println(c.getContenuto());%> </li>
                    <%    }  %>
                </ul>
                <div class="card-body">
                    <form action="/Microblog/AddCommentServlet" method="post">
                        <button type="submit" class="btn btn-primary" name="<%out.print(p.getId());%>">Commenta</button>
                    </form>
                </div>
            </div>
            <%     } %>
        </div>
    </div>
</body>

</html>