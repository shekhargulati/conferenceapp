<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>ConfSays: Learn about conferences better</title>

<!-- Bootstrap core CSS -->
<!-- Bootstrap core CSS -->
<link href="<c:url value="/css/bootstrap.css"></c:url>" rel="stylesheet">

<link href="<c:url value="/css/jumbotron.css"></c:url>" rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]>
    <script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value="/"/>">ConfSays</a>
			</div>

			<div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Conferences<b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/conferences"></c:url>">View All Conferences</a></li>
                    </ul>
                </li>
                <li><a href="<c:url value="quiz"></c:url>">OpenShift Quiz</a></li>
            </ul>
        </div>
			<!--/.navbar-collapse -->
		</div>
	</div>

	<div id="wrap">
		<div class="container">

			<div class="row">
				<div id="question" class="col-md-6">
					<div id="message" class="label label-primary">Press the start
						button to start.</div>
					<div id="questionText" class="lead"></div>

				</div>
				<div class="col-md-6">

					<button type="submit" class="btn btn-success" id="start">Start</button>
					<div id="answerBox" style="display: none;">
						<input type="text" class="col-sm-10" name="answer" id="answer"
							placeholder="Enter only yes or no">
						<button type="submit" class="btn btn-primary" id="next">Next</button>
						<button type="submit" class="btn btn-danger" id="end">End</button>
					</div>
				</div>
			</div>
		</div>
	</div>




	<hr>

	<div id="footer">
		<div class="container">
			<p class="text-muted credit">
				Made with &hearts; by <a href="https://twitter.com/shekhargulati/"
					target="_blank">Shekhar Gulati</a>.
			</p>
		</div>
	</div>


	<script src="<c:url value="/js/jquery-1.10.2.js"></c:url>"></script>
	<script src="<c:url value="/js/bootstrap.js"></c:url>"></script>
	<script src="<c:url value="/js/app.js"></c:url>"></script>

</body>
</html>