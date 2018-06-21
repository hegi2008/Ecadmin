var groupview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var table = [];
		var groups = this.groups;
		for(var i=0; i<groups.length; i++){
			table.push(this.renderGroup.call(this, target, i, groups[i], frozen));
		}
		$(container).html(table.join(''));
	},
	
	renderGroup: function(target, groupIndex, group, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var fields = $(target).datagrid('getColumnFields', frozen);
		
		var table = [];
		table.push('<div class="datagrid-group" group-index=' + groupIndex + '>');
		table.push('<table cellspacing="0" cellpadding="0" border="0" style="height:100%"><tbody>');
		table.push('<tr>');
		if ((frozen && (opts.rownumbers || opts.frozenColumns.length)) ||
				(!frozen && !(opts.rownumbers || opts.frozenColumns.length))){
			table.push('<td style="border:0;text-align:center;width:25px"><span class="datagrid-row-expander datagrid-row-collapse" style="display:inline-block;width:16px;height:16px;cursor:pointer">&nbsp;</span></td>');
		}
		table.push('<td style="border:0;">');
		if (!frozen){
			table.push('<span class="datagrid-group-title">');
			table.push(opts.groupFormatter.call(target, group.value, group.rows));
			table.push('</span>');
		}
		table.push('</td>');
		table.push('</tr>');
		table.push('</tbody></table>');
		table.push('</div>');
		
		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		var index = group.startIndex;
		for(var j=0; j<group.rows.length; j++) {
			var css = opts.rowStyler ? opts.rowStyler.call(target, index, group.rows[j]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			
			var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + index;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, index, group.rows[j]));
			table.push('</tr>');
			index++;
		}
		table.push('</tbody></table>');
		return table.join('');
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var expander = tt.closest('span.datagrid-row-expander');
			if (expander.length){
				var gindex = expander.closest('div.datagrid-group').attr('group-index');
				if (expander.hasClass('datagrid-row-collapse')){
					$(target).datagrid('collapseGroup', gindex);
				} else {
					$(target).datagrid('expandGroup', gindex);
				}
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target, rows){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		
		initCss();
		
		var groups = [];
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			var group = getGroup(row[opts.groupField]);
			if (!group){
				group = {
					value: row[opts.groupField],
					rows: [row]
				};
				groups.push(group);
			} else {
				group.rows.push(row);
			}
		}
		
		var index = 0;
		var newRows = [];
		for(var i=0; i<groups.length; i++){
			var group = groups[i];
			group.startIndex = index;
			index += group.rows.length;
			newRows = newRows.concat(group.rows);
		}
		
		state.data.rows = newRows;
		this.groups = groups;
		
		var that = this;
		setTimeout(function(){
			that.bindEvents(target);
		},0);
		
		function getGroup(value){
			for(var i=0; i<groups.length; i++){
				var group = groups[i];
				if (group.value == value){
					return group;
				}
			}
			return null;
		}
		function initCss(){
			if (!$('#datagrid-group-style').length){
				$('head').append(
					'<style id="datagrid-group-style">' +
					'.datagrid-group{height:25px;overflow:hidden;font-weight:bold;border-bottom:1px solid #ccc;}' +
					'</style>'
				);
			}
		}
	}
});

$.extend($.fn.datagrid.methods, {
    expandGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-expand')){
                expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
                group.next('table').show();
            }
            $(this).datagrid('fixRowHeight');
        });
    },
    collapseGroup:function(jq, groupIndex){
        return jq.each(function(){
            var view = $.data(this, 'datagrid').dc.view;
            var group = view.find(groupIndex!=undefined ? 'div.datagrid-group[group-index="'+groupIndex+'"]' : 'div.datagrid-group');
            var expander = group.find('span.datagrid-row-expander');
            if (expander.hasClass('datagrid-row-collapse')){
                expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
                group.next('table').hide();
            }
            $(this).datagrid('fixRowHeight');
        });
    }
});

$.extend(groupview, {
	refreshGroupTitle: function(target, groupIndex){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = this.groups[groupIndex];
		var span = dc.body2.children('div.datagrid-group[group-index=' + groupIndex + ']').find('span.datagrid-group-title');
		span.html(opts.groupFormatter.call(target, group.value, group.rows));
	},
	
	insertRow: function(target, index, row){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var group = null;
		var groupIndex;
		
		for(var i=0; i<this.groups.length; i++){
			if (this.groups[i].value == row[opts.groupField]){
				group = this.groups[i];
				groupIndex = i;
				break;
			}
		}
		if (group){
			if (index == undefined || index == null){
				index = state.data.rows.length;
			}
			if (index < group.startIndex){
				index = group.startIndex;
			} else if (index > group.startIndex + group.rows.length){
				index = group.startIndex + group.rows.length;
			}
			$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
			
			if (index >= group.startIndex + group.rows.length){
				_moveTr(index, true);
				_moveTr(index, false);
			}
			group.rows.splice(index - group.startIndex, 0, row);
		} else {
			group = {
				value: row[opts.groupField],
				rows: [row],
				startIndex: state.data.rows.length
			}
			groupIndex = this.groups.length;
			dc.body1.append(this.renderGroup.call(this, target, groupIndex, group, true));
			dc.body2.append(this.renderGroup.call(this, target, groupIndex, group, false));
			this.groups.push(group);
			state.data.rows.push(row);
		}
		
		this.refreshGroupTitle(target, groupIndex);
		
		function _moveTr(index,frozen){
			var serno = frozen?1:2;
			var prevTr = opts.finder.getTr(target, index-1, 'body', serno);
			var tr = opts.finder.getTr(target, index, 'body', serno);
			tr.insertAfter(prevTr);
		}
	},
	
	updateRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		$.fn.datagrid.defaults.view.updateRow.call(this, target, index, row);
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		this.refreshGroupTitle(target, groupIndex);
	},
	
	deleteRow: function(target, index){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var body = dc.body1.add(dc.body2);
		
		var tb = opts.finder.getTr(target, index, 'body', 2).closest('table.datagrid-btable');
		var groupIndex = parseInt(tb.prev().attr('group-index'));
		
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		
		var group = this.groups[groupIndex];
		if (group.rows.length > 1){
			group.rows.splice(index-group.startIndex, 1);
			this.refreshGroupTitle(target, groupIndex);
		} else {
			body.children('div.datagrid-group[group-index='+groupIndex+']').remove();
			for(var i=groupIndex+1; i<this.groups.length; i++){
				body.children('div.datagrid-group[group-index='+i+']').attr('group-index', i-1);
			}
			this.groups.splice(groupIndex, 1);
		}
		
		var index = 0;
		for(var i=0; i<this.groups.length; i++){
			var group = this.groups[i];
			group.startIndex = index;
			index += group.rows.length;
		}
	}
});

var detailview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		if (frozen){
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
		}
		
		var rows = state.data.rows;
		var fields = $(target).datagrid('getColumnFields', frozen);
		var table = [];
		table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
		for(var i=0; i<rows.length; i++) {
			// get the class and style attributes for this row
			var css = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			
			var cls = 'class="datagrid-row ' + (i % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + i;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + i + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
			table.push('</tr>');
			
			table.push('<tr style="display:none;">');
			if (frozen){
				table.push('<td colspan=' + (fields.length+2) + ' style="border-right:0">');
			} else {
				table.push('<td colspan=' + (fields.length) + '>');
			}
			table.push('<div class="datagrid-row-detail">');
			if (frozen){
				table.push('&nbsp;');
			} else {
				table.push(opts.detailFormatter.call(target, i, rows[i]));
			}
			table.push('</div>');
			table.push('</td>');
			table.push('</tr>');
			
		}
		table.push('</tbody></table>');
		
		$(container).html(table.join(''));
	},
	
	renderRow: function(target, fields, frozen, rowIndex, rowData){
		var opts = $.data(target, 'datagrid').options;
		
		var cc = [];
		if (frozen && opts.rownumbers){
			var rownumber = rowIndex + 1;
			if (opts.pagination){
				rownumber += (opts.pageNumber-1)*opts.pageSize;
			}
			cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+'</div></td>');
		}
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $(target).datagrid('getColumnOption', field);
			if (col){
				var value = rowData[field];	// the field value
				var css = col.styler ? (col.styler(value, rowData, rowIndex)||'') : '';
				var classValue = '';
				var styleValue = '';
				if (typeof css == 'string'){
					styleValue = css;
				} else if (cc){
					classValue = css['class'] || '';
					styleValue = css['style'] || '';
				}
				var cls = classValue ? 'class="' + classValue + '"' : '';
				var style = col.hidden ? 'style="display:none;' + styleValue + '"' : (styleValue ? 'style="' + styleValue + '"' : '');
				
				cc.push('<td field="' + field + '" ' + cls + ' ' + style + '>');
				
				if (col.checkbox){
					style = '';
				} else if (col.expander){
					style = "text-align:center;height:16px;";
				} else {
					style = styleValue;
					if (col.align){style += ';text-align:' + col.align + ';'}
					if (!opts.nowrap){
						style += ';white-space:normal;height:auto;';
					} else if (opts.autoRowHeight){
						style += ';height:auto;';
					}
				}
				
				cc.push('<div style="' + style + '" ');
				if (col.checkbox){
					cc.push('class="datagrid-cell-check ');
				} else {
					cc.push('class="datagrid-cell ' + col.cellClass);
				}
				cc.push('">');
				
				if (col.checkbox){
					cc.push('<input type="checkbox" name="' + field + '" value="' + (value!=undefined ? value : '') + '">');
				} else if (col.expander) {
					//cc.push('<div style="text-align:center;width:16px;height:16px;">');
					cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />');
					//cc.push('</div>');
				} else if (col.formatter){
					cc.push(col.formatter(value, rowData, rowIndex));
				} else {
					cc.push(value);
				}
				
				cc.push('</div>');
				cc.push('</td>');
			}
		}
		return cc.join('');
	},
	
	insertRow: function(target, index, row){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var panel = $(target).datagrid('getPanel');
		var view1 = dc.view1;
		var view2 = dc.view2;
		
		var isAppend = false;
		var rowLength = $(target).datagrid('getRows').length;
		if (rowLength == 0){
			$(target).datagrid('loadData',{total:1,rows:[row]});
			return;
		}
		
		if (index == undefined || index == null || index >= rowLength) {
			index = rowLength;
			isAppend = true;
			this.canUpdateDetail = false;
		}
		
		$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
		
		_insert(true);
		_insert(false);
		
		this.canUpdateDetail = true;
		
		function _insert(frozen){
			var v = frozen ? view1 : view2;
			var tr = v.find('tr[datagrid-row-index='+index+']');
			
			if (isAppend){
				var newDetail = tr.next().clone();
				tr.insertAfter(tr.next());
			} else {
				var newDetail = tr.next().next().clone();
			}
			newDetail.insertAfter(tr);
			newDetail.hide();
			if (!frozen){
				newDetail.find('div.datagrid-row-detail').html(opts.detailFormatter.call(target, index, row));
			}
		}
	},
	
	deleteRow: function(target, index){
		var opts = $.data(target, 'datagrid').options;
		var dc = $.data(target, 'datagrid').dc;
		var tr = opts.finder.getTr(target, index);
		tr.next().remove();
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		dc.body2.triggerHandler('scroll');
	},
	
	updateRow: function(target, rowIndex, row){
		var dc = $.data(target, 'datagrid').dc;
		var opts = $.data(target, 'datagrid').options;
		var cls = $(target).datagrid('getExpander', rowIndex).attr('class');
		$.fn.datagrid.defaults.view.updateRow.call(this, target, rowIndex, row);
		$(target).datagrid('getExpander', rowIndex).attr('class',cls);
		
		// update the detail content
		if (this.canUpdateDetail){
			var row = $(target).datagrid('getRows')[rowIndex];
			var detail = $(target).datagrid('getRowDetail', rowIndex);
			detail.html(opts.detailFormatter.call(target, rowIndex, row));
		}
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var tr = tt.closest('tr.datagrid-row');
			if (!tr.length){return}
			if (tt.hasClass('datagrid-row-expander')){
				var rowIndex = parseInt(tr.attr('datagrid-row-index'));
				if (tt.hasClass('datagrid-row-expand')){
					$(target).datagrid('expandRow', rowIndex);
				} else {
					$(target).datagrid('collapseRow', rowIndex);
				}
				$(target).datagrid('fixRowHeight');
				
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
		var ee = 'mouseover mouseout click dblclick contextmenu scroll';
		body.find('div.datagrid-row-detail').unbind().bind(ee, function(e){
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var t = $(target);
		var hasExpander = false;
		var fields = t.datagrid('getColumnFields',true).concat(t.datagrid('getColumnFields'));
		for(var i=0; i<fields.length; i++){
			var col = t.datagrid('getColumnOption', fields[i]);
			if (col.expander){
				hasExpander = true;
				break;
			}
		}
		if (!hasExpander){
			if (opts.frozenColumns && opts.frozenColumns.length){
				opts.frozenColumns[0].splice(0,0,{field:'_expander',expander:true,width:24,resizable:false,fixed:true});
			} else {
				opts.frozenColumns = [[{field:'_expander',expander:true,width:24,resizable:false,fixed:true}]];
			}
			
			var t = dc.view1.children('div.datagrid-header').find('table');
			var td = $('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');
			if ($('tr',t).length == 0){
				td.wrap('<tr></tr>').parent().appendTo($('tbody',t));
			} else if (opts.rownumbers){
				td.insertAfter(t.find('td:has(div.datagrid-header-rownumber)'));
			} else {
				td.prependTo(t.find('tr:first'));
			}
		}

		if (!state.bindDetailEvents){
			state.bindDetailEvents = true;
			var that = this;
			setTimeout(function(){
				that.bindEvents(target);
			},0);
		}
	},
	
	onAfterRender: function(target){
		var that = this;
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var panel = $(target).datagrid('getPanel');
		
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
		
		if (!state.onResizeColumn){
			state.onResizeColumn = opts.onResizeColumn;
		}
		if (!state.onResize){
			state.onResize = opts.onResize;
		}
		function resizeDetails(){
			var ht = dc.header2.find('table');
			var fr = ht.find('tr.datagrid-filter-row').hide();
			var ww = ht.width()-1;
			var details = dc.body2.find('div.datagrid-row-detail:visible')._outerWidth(ww);
			details.find('.easyui-fluid').trigger('_resize');
			fr.show();
		}
		
		opts.onResizeColumn = function(field, width){
			if (!opts.fitColumns){
				resizeDetails();				
			}
			var rowCount = $(target).datagrid('getRows').length;
			for(var i=0; i<rowCount; i++){
				$(target).datagrid('fixDetailRowHeight', i);
			}
			
			// call the old event code
			state.onResizeColumn.call(target, field, width);
		};
		opts.onResize = function(width, height){
			if (opts.fitColumns){
				resizeDetails();				
			}
			state.onResize.call(panel, width, height);
		};
		
		this.canUpdateDetail = true;	// define if to update the detail content when 'updateRow' method is called;
		
		var footer = dc.footer1.add(dc.footer2);
		footer.find('span.datagrid-row-expander').css('visibility', 'hidden');
		$(target).datagrid('resize');
	}
});

$.extend($.fn.datagrid.methods, {
	fixDetailRowHeight: function(jq, index){
		return jq.each(function(){
			var opts = $.data(this, 'datagrid').options;
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
			var dc = $.data(this, 'datagrid').dc;
			var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
			var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
			// fix the detail row height
			if (tr2.is(':visible')){
				tr1.css('height', '');
				tr2.css('height', '');
				var height = Math.max(tr1.height(), tr2.height());
				tr1.css('height', height);
				tr2.css('height', height);
			}
			dc.body2.triggerHandler('scroll');
		});
	},
	getExpander: function(jq, index){	// get row expander object
		var opts = $.data(jq[0], 'datagrid').options;
		return opts.finder.getTr(jq[0], index).find('span.datagrid-row-expander');
	},
	// get row detail container
	getRowDetail: function(jq, index){
		var opts = $.data(jq[0], 'datagrid').options;
		var tr = opts.finder.getTr(jq[0], index, 'body', 2);
		return tr.next().find('div.datagrid-row-detail');
	},
	expandRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-expand')){
				expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.show();
				tr2.show();
				$(this).datagrid('fixDetailRowHeight', index);
				if (opts.onExpandRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onExpandRow.call(this, index, row);
				}
			}
		});
	},
	collapseRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-collapse')){
				expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.hide();
				tr2.hide();
				dc.body2.triggerHandler('scroll');
				if (opts.onCollapseRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onCollapseRow.call(this, index, row);
				}
			}
		});
	}
});

$.extend($.fn.datagrid.defaults, {
	rowHeight: 25,
	onBeforeFetch: function(page){},
	onFetch: function(page, rows){}
});

var scrollview = $.extend({}, $.fn.datagrid.defaults.view, {
	render: function(target, container, frozen){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var rows = this.rows || [];
		if (!rows.length) {
			return;
		}
		var fields = $(target).datagrid('getColumnFields', frozen);
		
		if (frozen){
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){
				return;
			}
		}
		
		var index = this.index;
		var table = ['<div class="datagrid-btable-top"></div>',
		             '<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>'];
		for(var i=0; i<rows.length; i++) {
			var css = opts.rowStyler ? opts.rowStyler.call(target, index, rows[i]) : '';
			var classValue = '';
			var styleValue = '';
			if (typeof css == 'string'){
				styleValue = css;
			} else if (css){
				classValue = css['class'] || '';
				styleValue = css['style'] || '';
			}
			var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';
			var style = styleValue ? 'style="' + styleValue + '"' : '';
			// get the class and style attributes for this row
//			var cls = (index % 2 && opts.striped) ? 'class="datagrid-row datagrid-row-alt"' : 'class="datagrid-row"';
//			var styleValue = opts.rowStyler ? opts.rowStyler.call(target, index, rows[i]) : '';
//			var style = styleValue ? 'style="' + styleValue + '"' : '';
			var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + index;
			table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');
			table.push(this.renderRow.call(this, target, fields, frozen, index, rows[i]));
			table.push('</tr>');
			
			// render the detail row
			if (opts.detailFormatter){
				table.push('<tr style="display:none;">');
				if (frozen){
					table.push('<td colspan=' + (fields.length+2) + ' style="border-right:0">');
				} else {
					table.push('<td colspan=' + (fields.length) + '>');
				}
				table.push('<div class="datagrid-row-detail">');
				if (frozen){
					table.push('&nbsp;');
				} else {
					table.push(opts.detailFormatter.call(target, index, rows[i]));
				}
				table.push('</div>');
				table.push('</td>');
				table.push('</tr>');
			}

			index++;
		}
		table.push('</tbody></table>');
		table.push('<div class="datagrid-btable-bottom"></div>');
		
		$(container).html(table.join(''));
	},
	
	renderRow: function(target, fields, frozen, rowIndex, rowData){
		var opts = $.data(target, 'datagrid').options;
		
		var cc = [];
		if (frozen && opts.rownumbers){
			var rownumber = rowIndex + 1;
			if (opts.pagination){
				rownumber += (opts.pageNumber-1)*opts.pageSize;
			}
			cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'+rownumber+'</div></td>');
		}
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			var col = $(target).datagrid('getColumnOption', field);
			if (col){
				var value = rowData[field];	// the field value
				var css = col.styler ? (col.styler(value, rowData, rowIndex)||'') : '';
				var classValue = '';
				var styleValue = '';
				if (typeof css == 'string'){
					styleValue = css;
				} else if (cc){
					classValue = css['class'] || '';
					styleValue = css['style'] || '';
				}
				var cls = classValue ? 'class="' + classValue + '"' : '';
				var style = col.hidden ? 'style="display:none;' + styleValue + '"' : (styleValue ? 'style="' + styleValue + '"' : '');
				
				cc.push('<td field="' + field + '" ' + cls + ' ' + style + '>');
				
				if (col.checkbox){
					style = '';
				} else if (col.expander){
					style = "text-align:center;height:16px;";
				} else {
					style = styleValue;
					if (col.align){style += ';text-align:' + col.align + ';'}
					if (!opts.nowrap){
						style += ';white-space:normal;height:auto;';
					} else if (opts.autoRowHeight){
						style += ';height:auto;';
					}
				}
				
				cc.push('<div style="' + style + '" ');
				if (col.checkbox){
					cc.push('class="datagrid-cell-check ');
				} else {
					cc.push('class="datagrid-cell ' + col.cellClass);
				}
				cc.push('">');
				
				if (col.checkbox){
					cc.push('<input type="checkbox" name="' + field + '" value="' + (value!=undefined ? value : '') + '">');
				} else if (col.expander) {
					//cc.push('<div style="text-align:center;width:16px;height:16px;">');
					cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />');
					//cc.push('</div>');
				} else if (col.formatter){
					cc.push(col.formatter(value, rowData, rowIndex));
				} else {
					cc.push(value);
				}
				
				cc.push('</div>');
				cc.push('</td>');
			}
		}
		return cc.join('');
	},
	
	bindEvents: function(target){
		var state = $.data(target, 'datagrid');
		var dc = state.dc;
		var opts = state.options;
		var body = dc.body1.add(dc.body2);
		var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
		body.unbind('click').bind('click', function(e){
			var tt = $(e.target);
			var tr = tt.closest('tr.datagrid-row');
			if (!tr.length){return}
			if (tt.hasClass('datagrid-row-expander')){
				var rowIndex = parseInt(tr.attr('datagrid-row-index'));
				if (tt.hasClass('datagrid-row-expand')){
					$(target).datagrid('expandRow', rowIndex);
				} else {
					$(target).datagrid('collapseRow', rowIndex);
				}
				$(target).datagrid('fixRowHeight');
				
			} else {
				clickHandler(e);
			}
			e.stopPropagation();
		});
	},
	
	onBeforeRender: function(target){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var view = this;
		
		state.data.firstRows = state.data.rows;
		state.data.rows = [];

		opts.finder = $.extend({}, $.fn.datagrid.defaults.finder, {
			getRow: function(t, p){
				var index = (typeof p == 'object') ? p.attr('datagrid-row-index') : p;
				var row = $.data(t, 'datagrid').data.rows[index];
				if (!row){
					var v = $(t).datagrid('options').view;
					row = v.rows[index - v.index];
				}
				return row;
			}
		});
		
		dc.body1.add(dc.body2).empty();
		this.rows = [];	// the rows to be rendered
		this.r1 = this.r2 = [];	// the first part and last part of rows
		
		init();
		createHeaderExpander();
		
		function init(){
			// erase the onLoadSuccess event, make sure it can't be triggered
			state.onLoadSuccess = opts.onLoadSuccess;
			opts.onLoadSuccess = function(){};
			setTimeout(function(){
				dc.body2.unbind('.datagrid').bind('scroll.datagrid', function(e){
					if (state.onLoadSuccess){
						opts.onLoadSuccess = state.onLoadSuccess;	// restore the onLoadSuccess event
						state.onLoadSuccess = undefined;
					}
					if (view.scrollTimer){
						clearTimeout(view.scrollTimer);
					}
					view.scrollTimer = setTimeout(function(){
						scrolling.call(view);
					}, 50);
				});
				dc.body2.triggerHandler('scroll.datagrid');
			}, 0);
		}
		function scrolling(){
			if (!opts.finder.getRows(target).length){
				reload.call(this);
			} else {
				if (!dc.body2.is(':visible')){return}
				var headerHeight = dc.view2.children('div.datagrid-header').outerHeight();
				
				var topDiv = dc.body2.children('div.datagrid-btable-top');
				var bottomDiv = dc.body2.children('div.datagrid-btable-bottom');
				if (!topDiv.length || !bottomDiv.length){return;}
				var top = topDiv.position().top + topDiv._outerHeight() - headerHeight;
				var bottom = bottomDiv.position().top - headerHeight;

				if (top > dc.body2.height() || bottom < 0){
					reload.call(this);
				} else if (top > 0){
					var page = Math.floor(this.index/opts.pageSize);
					this.getRows.call(this, target, page, function(rows){
						this.r2 = this.r1;
						this.r1 = rows;
						this.index = (page-1)*opts.pageSize;
						this.rows = this.r1.concat(this.r2);
						this.populate.call(this, target);
					});
				} else if (bottom < dc.body2.height()){// 需要加载下一页的情况
					if (state.data.rows.length >= state.data.total){
						return;
					}
					var page = Math.floor(this.index/opts.pageSize)+2;
					if (this.r2.length){
						page++;
					}
					this.getRows.call(this, target, page, function(rows){
						if (!this.r2.length){
							this.r2 = rows;
						} else {
							this.r1 = this.r2;
							this.r2 = rows;
							this.index += opts.pageSize;
						}
						this.rows = this.r1.concat(this.r2);
						this.populate.call(this, target);
					});
				}
			}
			
			function reload(){
				var top = $(dc.body2).scrollTop();
				var index = Math.floor(top/25);
				var page = Math.floor(index/opts.pageSize) + 1;
				
				this.getRows.call(this, target, page, function(rows){
					this.index = (page-1)*opts.pageSize;
					this.rows = rows;
					this.r1 = rows;
					this.r2 = [];
					this.populate.call(this, target);
					if(rows && rows.length > 0){   
					    dc.body2.triggerHandler('scroll.datagrid');   
					}
				});
			}
		}
		function createHeaderExpander(){
			if (!opts.detailFormatter){return}
			
			var t = $(target);
			var hasExpander = false;
			var fields = t.datagrid('getColumnFields',true).concat(t.datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = t.datagrid('getColumnOption', fields[i]);
				if (col.expander){
					hasExpander = true;
					break;
				}
			}
			if (!hasExpander){
				if (opts.frozenColumns && opts.frozenColumns.length){
					opts.frozenColumns[0].splice(0,0,{field:'_expander',expander:true,width:24,resizable:false,fixed:true});
				} else {
					opts.frozenColumns = [[{field:'_expander',expander:true,width:24,resizable:false,fixed:true}]];
				}
				
				var t = dc.view1.children('div.datagrid-header').find('table');
				var td = $('<td rowspan="'+opts.frozenColumns.length+'"><div class="datagrid-header-expander" style="width:24px;"></div></td>');
				if ($('tr',t).length == 0){
					td.wrap('<tr></tr>').parent().appendTo($('tbody',t));
				} else if (opts.rownumbers){
					td.insertAfter(t.find('td:has(div.datagrid-header-rownumber)'));
				} else {
					td.prependTo(t.find('tr:first'));
				}
			}
			
			setTimeout(function(){
				view.bindEvents(target);
			},0);
		}
	},
	
	onAfterRender: function(target){
		$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
		var dc = $.data(target, 'datagrid').dc;
		var footer = dc.footer1.add(dc.footer2);
		footer.find('span.datagrid-row-expander').css('visibility', 'hidden');
	},
	
	getRows: function(target, page, callback){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var index = (page-1)*opts.pageSize;
		
		if (opts.onBeforeFetch.call(target, page) == false){return}
		
		var rows = state.data.firstRows.slice(index, index+opts.pageSize);
		if (rows.length){
			opts.onFetch.call(target, page, rows);
			callback.call(this, rows);
		} else {
			var param = $.extend({}, opts.queryParams, {
				page: page,
				rows: opts.pageSize
			});
			if (opts.sortName){
				$.extend(param, {
					sort: opts.sortName,
					order: opts.sortOrder
				});
			}
			if (opts.onBeforeLoad.call(target, param) == false) return;
			
			$(target).datagrid('loading');
			var result = opts.loader.call(target, param, function(data){
				$(target).datagrid('loaded');
				var data = opts.loadFilter.call(target, data);
				opts.onFetch.call(target, page, data.rows);
				if (data.rows && data.rows.length){
					callback.call(opts.view, data.rows);
				} else {
					opts.onLoadSuccess.call(target, data);
				}
			}, function(){
				$(target).datagrid('loaded');
				opts.onLoadError.apply(target, arguments);
			});
			if (result == false){
				$(target).datagrid('loaded');
				if (!state.data.firstRows.length){
					opts.onFetch.call(target, page, state.data.firstRows);
					opts.onLoadSuccess.call(target, state.data);
				}
			}
		}
	},
	
	populate: function(target){
		var state = $.data(target, 'datagrid');
		var opts = state.options;
		var dc = state.dc;
		var rowHeight = opts.rowHeight;
		
		if (this.rows.length){
			opts.view.render.call(opts.view, target, dc.body2, false);
			opts.view.render.call(opts.view, target, dc.body1, true);
			var body = dc.body1.add(dc.body2);
			body.children('div.datagrid-btable-top').css('height', this.index*rowHeight);
			body.children('div.datagrid-btable-bottom').css('height', state.data.total*rowHeight - this.rows.length*rowHeight - this.index*rowHeight);
			
			var r = [];
			for(var i=0; i<this.index; i++){
				r.push({});
			}
			state.data.rows = r.concat(this.rows);
			
			var spos = dc.body2.scrollTop();
			$(target).datagrid('setSelectionState');
			dc.body2.scrollTop(spos);

			opts.onLoadSuccess.call(target, {
				total: state.data.total,
				rows: this.rows
			});
		}
	},

	insertRow: function(target, index, row){
		var state = $.data(target, 'datagrid');
		var data = state.data;
		
		if (index == undefined || index == null) index = data.rows.length;
		if (index > data.rows.length) index = data.rows.length;
		$.fn.datagrid.defaults.view.insertRow.call(this, target, index, row);
		if (data.firstRows && index < data.firstRows.length){
			data.firstRows.splice(index, 0, row);
		}
	},

	deleteRow: function(target, index){
		var data = $(target).datagrid('getData');
		$.fn.datagrid.defaults.view.deleteRow.call(this, target, index);
		if (data.firstRows){
			data.firstRows.splice(index, 1);
		}
	}
});

$.fn.datagrid.methods.baseScrollTo = $.fn.datagrid.methods.scrollTo;
$.extend($.fn.datagrid.methods, {
	gotoPage: function(jq, page, callback){
		return jq.each(function(){
			var target = this;
			var opts = $(target).datagrid('options');
			opts.view.getRows.call(opts.view, target, page, function(rows){
				this.index = (page-1)*opts.pageSize;
				this.rows = rows;
				this.r1 = rows;
				this.r2 = [];
				this.populate.call(this, target);
				$(target).data('datagrid').dc.body2.scrollTop(this.index * opts.rowHeight);
				if (callback){
					callback();
				}
			});
		});
	},
	scrollTo: function(jq, index){
		return jq.each(function(){
			var target = this;
			var opts = $(target).datagrid('options');
			var view = opts.view;
			if (index >= view.index && index < view.index+view.rows.length){
				$(target).datagrid('baseScrollTo', index);
			} else {
				var page = Math.floor(index/opts.pageSize) + 1;
				$.fn.datagrid.methods.gotoPage($(this), page, function(){
					setTimeout(function(){
						$(target).datagrid('baseScrollTo', index);
					}, 0);
				});				
			}
		});
	}
});

$.extend($.fn.datagrid.methods, {
	fixDetailRowHeight: function(jq, index){
		return jq.each(function(){
			var opts = $.data(this, 'datagrid').options;
			var dc = $.data(this, 'datagrid').dc;
			var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
			var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
			// fix the detail row height
			if (tr2.is(':visible')){
				tr1.css('height', '');
				tr2.css('height', '');
				var height = Math.max(tr1.height(), tr2.height());
				tr1.css('height', height);
				tr2.css('height', height);
			}
			dc.body2.triggerHandler('scroll');
		});
	},
	getExpander: function(jq, index){	// get row expander object
		var opts = $.data(jq[0], 'datagrid').options;
		return opts.finder.getTr(jq[0], index).find('span.datagrid-row-expander');
	},
	// get row detail container
	getRowDetail: function(jq, index){
		var opts = $.data(jq[0], 'datagrid').options;
		var tr = opts.finder.getTr(jq[0], index, 'body', 2);
		return tr.next().find('div.datagrid-row-detail');
	},
	expandRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-expand')){
				expander.removeClass('datagrid-row-expand').addClass('datagrid-row-collapse');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.show();
				tr2.show();
				$(this).datagrid('fixDetailRowHeight', index);
				if (opts.onExpandRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onExpandRow.call(this, index, row);
				}
			}
		});
	},
	collapseRow: function(jq, index){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var dc = $.data(this, 'datagrid').dc;
			var expander = $(this).datagrid('getExpander', index);
			if (expander.hasClass('datagrid-row-collapse')){
				expander.removeClass('datagrid-row-collapse').addClass('datagrid-row-expand');
				var tr1 = opts.finder.getTr(this, index, 'body', 1).next();
				var tr2 = opts.finder.getTr(this, index, 'body', 2).next();
				tr1.hide();
				tr2.hide();
				dc.body2.triggerHandler('scroll');
				if (opts.onCollapseRow){
					var row = $(this).datagrid('getRows')[index];
					opts.onCollapseRow.call(this, index, row);
				}
			}
		});
	}
});

/**
 * Created with JetBrains WebStorm.
 * User: cao.guanghui
 * Date: 13-6-26
 * Time: 下午11:27
 * To change this template use File | Settings | File Templates.
 */
$.extend($.fn.datagrid.methods, {
    /**
     * 开打提示功能（基于1.3.3+版本）
     * @param {} jq
     * @param {} params 提示消息框的样式
     * @return {}
     */
    doCellTip:function (jq, params) {
        function showTip(showParams, td, e, dg) {
            //无文本，不提示。
            if ($(td).text() == "") return;
            params = params || {};
            var options = dg.data('datagrid');
            
            var styler = 'style="';
            if(showParams.width){
            	styler = styler + "width:" + showParams.width + ";";
            }
            if(showParams.maxWidth){
            	styler = styler + "max-width:" + showParams.maxWidth + ";";
            }
            if(showParams.minWidth){
            	styler = styler + "min-width:" + showParams.minWidth + ";";
            }
            styler = styler + '"';
            showParams.content = '<div class="tipcontent" ' + styler + '>' + showParams.content + '</div>';
            $(td).tooltip({
                content:showParams.content,
                trackMouse:true,
                position:params.position,
                onHide:function () {
                    $(this).tooltip('destroy');
                },
                onShow:function () {
                    var tip = $(this).tooltip('tip');
                    if(showParams.tipStyler){
                        tip.css(showParams.tipStyler);
                    }
                    if(showParams.contentStyler){
                        tip.find('div.tipcontent').css(showParams.contentStyler);
                    }
                }
            }).tooltip('show');
        };
        return jq.each(function () {
            var grid = $(this);
            var options = $(this).data('datagrid');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').each(function () {
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field='+params.itemid+']', {
                        'mouseover':function (e) {
                            //if($(this).attr('field')===undefined) return;
                            var that = this;
                            var setField = null;
                            if(params.specialShowFields && params.specialShowFields.sort){
                                for(var i=0; i<params.specialShowFields.length; i++){
                                    if(params.specialShowFields[i].field == $(this).attr('field')){
                                        setField = params.specialShowFields[i];
                                    }
                                }
                            }
                            if(setField==null){
                                options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');
                                var factContentWidth = options.factContent.width();
                                params.content = $(this).text();
                                if (params.onlyShowInterrupt) {
                                    if (factContentWidth > $(this).width()) {
                                        showTip(params, this, e, grid);
                                    }
                                } else {
                                    showTip(params, this, e, grid);
                                }
                            }else{
                                panel.find('.datagrid-body').each(function(){
                                    var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');
                                    trs.each(function(){
                                        var td = $(this).find('> td[field="' + setField.showField + '"]');
                                        if(td.length){
                                            params.content = td.text();
                                        }
                                    });
                                });
                                showTip(params, this, e, grid);
                            }
                        },
                        'mouseout':function (e) {
                            if (options.factContent) {
                                options.factContent.remove();
                                options.factContent = null;
                            }
                        }
                    });
                });
            }
        });
    },
    /**
     * 关闭消息提示功能（基于1.3.3版本）
     * @param {} jq
     * @return {}
     */
    cancelCellTip:function (jq) {
        return jq.each(function () {
            var data = $(this).data('datagrid');
            if (data.factContent) {
                data.factContent.remove();
                data.factContent = null;
            }
            var panel = $(this).datagrid('getPanel').panel('panel');
            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
        });
    }
});

//新定义一个单元格编辑的方法
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var ___editIndex = undefined;
Base.endDatagridEditing = function (id){
	if (___editIndex == undefined){return true}
	if ($('#'+id).datagrid('validateRow', ___editIndex)){
		$('#'+id).datagrid('endEdit', ___editIndex);
		___editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
Base.onClickCell = function (index,field,value,id){
	if (Base.endDatagridEditing(id)){
		$('#'+id).datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		___editIndex = index;
	}
}

Base.onClickRowEdit = function (index,field,value,id){
	if (___editIndex != index){
        if (Base.endDatagridEditing(id)){
            $('#'+id).datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#'+id).datagrid('getEditor', {index:index,field:field});
            if (ed){
            	// 第一个focus
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            ___editIndex = index;
        } else {
        	$('#'+id).datagrid('selectRow', ___editIndex);
        }
    }
}
/*创建datagrid表头右键简单的menu 暂时放置在这里
 * cjh
 * */
function ___createDatagridColumnMenu(cmenu,id){
    cmenu = $('<div/>').appendTo('body');
    cmenu.menu({
        onClick: function(item){
            if (item.iconCls == 'fa-check'){
                $('#'+id).datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                $('#'+id).datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'fa-check'
                });
            }
        }
    });
    var fields = $('#'+id).datagrid('getColumnFields');
    for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = $('#'+id).datagrid('getColumnOption', field);
        cmenu.menu('appendItem', {
            text: col.title,
            name: field,
            iconCls: 'fa-check'
        });
    }
    return cmenu;
}
/** 
 * 检查字符串是否为合法手机号码 Email地址 身份证号码
 */  
$.extend($.fn.validatebox.defaults.rules, {
	phone: {    
     validator: function isPhone(value) {
			value == $(this).val(); 
         var bValidate = RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(value);  
         if (bValidate) {  
             return true;  
         } else {
         	return false;  
         } 
     },
     message: '您输入的手机号码不正确'
 },
/* email: {
		validator:   function isEmail(value) {
	    	value == $(this).val(); 
	        var bValidate = RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(value);  
	        if (bValidate) {  
	            return true;  
	        } else {
	        	return false;  
	        } 
	   	},
	   	message: '您输入的电子邮箱不正确'
	},*/
	idcard: {
		validator:   function checkIdcard(num){
			num = $(this).val();
	        num = num.toUpperCase();
	        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	        if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
	        {
	            return false;
	        }
	        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	        //下面分别分析出生日期和校验位
	        var len, re;
	        len = num.length;
	        if (len == 15)
	        {
	            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
	            var arrSplit = num.match(re);
	     
	            //检查生日日期是否正确
	            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
	            var bGoodDay;
	            bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	            if (!bGoodDay)
	            {
	                return false;
	            }
	            else
	            {
	                    //将15位身份证转成18位
	                    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	                    var nTemp = 0, i;
	                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
	                    for(i = 0; i < 17; i ++)
	                    {
	                        nTemp += num.substr(i, 1) * arrInt[i];
	                    }
	                    num += arrCh[nTemp % 11];
	                    return true;
	            }
	        }
	        if (len == 18)
	        {
	            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
	            var arrSplit = num.match(re);
	     
	            //检查生日日期是否正确
	            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
	            var bGoodDay;
	            bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	            if (!bGoodDay)
	            {
	                return false;
	            }
	        else
	        {
	            //检验18位身份证的校验码是否正确。
	            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	            var valnum;
	            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	            var nTemp = 0, i;
	            for(i = 0; i < 17; i ++)
	            {
	                nTemp += num.substr(i, 1) * arrInt[i];
	            }
	            valnum = arrCh[nTemp % 11];
	            if (valnum != num.substr(17, 1))
	            {
	                return false;
	            }
	            return true;
	        }
	        }
	        return false;
	    },
	   	message: '您输入的身份证号不正确'
	},
	url: {
		validator: function(value){
			return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
		},
		message: '您输入的不合URL格式要求'
	},
	zipcode:{
		validator:function(value){
			return /[0-9]\d{5}(?!\d)/.test(value); //修改/[1-9]\d{5}(?!\d)/.test(value);
		},
		message: '您输入的邮编不正确'
	},
	ip:{
		validator:function(value){
			return /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/.test(value);
		},
		message: '您输入的IP地址格式不正确'
	},
	integer:{
		validator:function(value){
			return /^-?[1-9]\d*$/.test(value);
		},
		message: '只能输入整数'
	},
	chinese:{
		validator:function(value){
		  return /^[\u4e00-\u9fa5]+$/.test(value);
		},
		message: '只能输入中文'
	},
	number:{
		validator:function(value,param){
			try{
				parseInt(value);
				parseFloat(value);
			}catch(e){//非数字
				return false;
			}
			if(!jQuery.isArray(param))return true;
			if(param[0]==='' && param[1]!==''){
				this.message = "输入的数值必须小于或等于{1}";
				return Number(value) <= Number(param[1]);
			}
			else if(param[0] !== '' && param[1]===''){
				this.message = "输入的数值必须大于或等于{0}";
				return Number(value) >= Number(param[0]);
			}
			else{
				this.message = "输入的数值必须在{0}到{1}之间";
				return(Number(value) >= Number(param[0])) && (value <= Number(param[1]));
			}
			return true;
		},
		message: '输入的数值必须在{0}到{1}之间'
	}
});
if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '页,共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
$.map(['validatebox','textbox','filebox','searchbox',
		'combo','combobox','combogrid','combotree',
		'datebox','datetimebox','numberbox',
		'spinner','numberspinner','timespinner','datetimespinner'], function(plugin){
	if ($.fn[plugin]){
		$.fn[plugin].defaults.missingMessage = '该输入项为必输项';
	}
});
if ($.fn.validatebox){
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText
	});
}
if ($.fn.datetimespinner){
	$.fn.datetimespinner.defaults.selections = [[0,4],[5,7],[8,10],[11,13],[14,16],[17,19]]
}