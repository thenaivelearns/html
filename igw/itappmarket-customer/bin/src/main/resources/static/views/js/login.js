let loginVue = new Vue({
              el: '#login-box',
              data: {
                userName: '',
                password: '',
                info: '',
                show:false,
                openeye: '../image/openEyes.png',
                pwdType: 'password'
              },
              methods: {
                loginCheck: function (message) {
                	var param = {userName: this.userName, password: this.password};
                	loginCheck(param).then(res => {
                         this.show = false;
                         location.href = '/home';
                	})
                	.catch(err => {
                		 this.info = "\n错误码:" + err.data.errorCode +
                         "\n错误描述：" + err.data.errorMessage;
                          this.show = true;
                	});
                }
              }
            });