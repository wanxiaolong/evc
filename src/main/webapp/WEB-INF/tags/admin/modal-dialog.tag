<%@ tag language="java" pageEncoding="utf-8"%>

<%@ attribute name="title" required="true" type="java.lang.String" description="dialog title" %>
<%@ attribute name="action" required="true" type="java.lang.String" description="dialog action" %>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">${title}</h4>
			</div>
			<div class="modal-body">
				<jsp:doBody/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="confirm-update">${action}</button>
			</div>
		</div>
	</div>
</div>