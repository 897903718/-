(function(win,doc,undefined){
	var error = function(){
		this.btn = $('.btn');
		this.init();
	}
	error.prototype = {
		init:function(){
			icityJs = win.icityJs ? win.icityJs : win.chanlyAndroidJs ? win.chanlyAndroidJs : win.myjs ? win.myjs : undefined;
			this.btn.tap(function(){
				// console.log(111);
				if(icityJs && icityJs.reload){
					icityJs.reload();
				}
			})
		}
	}

	var javame = function(){}
	win.javame = javame;
	doc.addEventListener('DOMContentLoaded', function(){new error();}, false);
})(window,document)
