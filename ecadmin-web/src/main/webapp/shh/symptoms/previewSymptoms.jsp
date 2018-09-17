<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>久远银海-新闻中心</title>
    <style type="text/css">
        .div-after {
        }

        .div-after:after {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden;
        }

        .news-article-content {
            width: 1200px;
            margin: 0 auto 40px;
        }

        .news-article-content .news-history span {
            margin-left: 10px;
        }

        .news-article-content .news-title {
            font-size: 24px;
            color: #333;
            margin: 60px auto 36px;
            text-align: center;
        }

        .news-article-content .news-time {
            font-size: 12px;
            color: #666;
            margin: 0 auto;
            text-align: center;
        }

        .news-article-content .news-time span {
            margin-right: 17px;
        }

        .news-article-content .news-article {
            width: 680px;
            margin: 13px auto 0;
            border-top: 1px #aaa dashed;
            padding: 30px 10px;
        }

        .news-article-content .news-imgshow {
            width: 680px;
            height: 380px;
            margin: 0 auto 24px;
            border: solid 1px #dedede;
            background-position: center;
            background-repeat: no-repeat;
            background-size: contain;
        }
    </style>
</head>
<body>
<div class="news-article-content div-after">
    <div class="news-left">
        <p class="news-title">${ae.title }</p>
        <div class="news-time"><span id="add_time">${ae.add_time }</span><span>|</span>来源：<span
                id="add_username">${ae.fromto }</span></div>
        <div class="news-article">
            <div class="news-pshow">${ae.content }</div>
        </div>
    </div>
</div>
</body>
</html>