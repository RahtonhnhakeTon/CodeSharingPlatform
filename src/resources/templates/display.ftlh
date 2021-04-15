<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <pre id="code_snippet"><code>${code}</code></pre>
    <span id="load_date">${date}</span>
    <#if trestrict>
    <span id="time_restriction">will expire in ${time}</span>
    </#if>
    <#if vrestrict>
    <span id="views_restriction">Can be viewed ${views}x more...</span>
    </#if>
</body>
</html>