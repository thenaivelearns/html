let server = new Vue({            
         methods: {
                  form(url, params, type) {
                    return new Promise((resolve, reject) => {
            	            axios({
            	                method: type,
            	                params: params,
            	                headers: {
                                      "Content-Type": "multipart/form-data"
                                },
            	                url:url
            	            })
            	                .then(res => {
            	                	if(res.data.msgCode == '403') {
             	                		this.$alert(res.data.msgDesc,'提示',{
            								confirmButtonText:'确定',
            								type:"success",
            								callback:action => {
            									window.top.location.href="/static/views/page/home/login.html";
            								 }
            						      })
             	                	} else {
                                        resolve(res)
             	                	}
                                })
            	                .catch(err => {
            	                	 //请求失败处理
            	                    console.log('error.response.status : ' + err.response.status);
            	                    reject(err.response)
            	                })
            	        })
                  },
             	  get(url, params, type) {
            	        return new Promise((resolve, reject) => {
            	            axios({
            	                method: "get",
            	                params: params,
            	                url:url
            	            })
            	                .then(res => {
            	                	if(res.data.msgCode == '403') {
             	                		this.$alert(res.data.msgDesc,'提示',{
            								confirmButtonText:'确定',
            								type:"success",
            								callback:action => {
            									window.top.location.href="/static/views/page/home/login.html";
            								 }
            						      })
             	                	} else {
                                        resolve(res)
             	                	}
                                })
            	                .catch(err => {
            	                	 //请求失败处理
            	                    console.log('error.response.status : ' + err.response.status);
            	                    reject(err.response)
            	                })
            	        })
            	     },
            	     post(url, data) {
             	        return new Promise((resolve, reject) => {
             	            axios({
             	                method: "post",
             	                data: data,
             	                url:url
             	            })
             	                .then(res => {
             	                	if(res.data.msgCode == '403') {
            							window.top.location.href="/static/views/page/home/login.html";
             	                	} else {
                                        resolve(res)
             	                	}
                                 })
             	                .catch(err => {
             	                	 //请求失败处理
             	                    reject(err.response)
             	                })
             	        })
             	     }
                 }
            });

