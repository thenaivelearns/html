/*let homeVue = new Vue({
              el: '#homeDiv',
              data: {
                info: ''
              },
              methods: {
                jumpToLoginPage() {
                    window.location = "/static/views/page/login.html";
                },
                logout: function (message) {
                  axios({
                      method: 'post',
                      url: '/logout',
                      data: {
                        userName: this.userName,
                        password: this.password
                      }
                  })
                  .then((response) => {
                    //请求成功处理
                    console.log('response.status : ' + response.status);
                    this.show = false;
                    //直接跳转到登录页
                    this.jumpToLoginPage();
                  })
                  .catch((error) => {
                    //请求失败处理
                    console.log('error.response.status : ' + error.response.status);
                    this.info = "\n错误码:" + error.response.data.errorCode +
                                "\n错误描述：" + error.response.data.errorMessage;
                    alert(this.info);
                    //直接跳转到登录页
                    this.jumpToLoginPage();
                  });
                }
              }
            });*/