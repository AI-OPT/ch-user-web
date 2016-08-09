<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "No-cache");

    String base = request.getContextPath();
    request.setAttribute("_base", base);

    String slpRes=base+"/resources/slpoperate";
    request.setAttribute("_slpres", slpRes);
    
    String accountBalanceLink=base+"/account/balance/index";
    request.setAttribute("accountBalanceLink", accountBalanceLink);

    String accountRechargeOneLink=base+"/account/recharge/one";
    request.setAttribute("accountRechargeOneLink", accountRechargeOneLink);
%>
<script>
    var _base = "${_base}";
</script>

<script src="${_base}/resources/spm_modules/jquery/1.9.1/jquery.js"></script>
<script src="${_base}/resources/spm_modules/bootstrap/dist/js/bootstrap.js"></script>
<script src="${_base}/resources/spm_modules/seajs/2.3.0/dist/sea.js"></script>
<script src="${_base}/resources/spm_modules/seajs/seajs-css.js"></script>
<script src="${_base}/resources/spm_modules/app/core/config.js"></script>

<!-- UED -->
<script src="${_slpres }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpres }/scripts/metismenu.js"></script>

<link rel="stylesheet" type="text/css" href="${_slpres}/styles/bootstrap.css">

