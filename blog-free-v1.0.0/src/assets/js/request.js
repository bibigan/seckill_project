import axios from 'axios'
export default {
    common: {
        method: 'GET',
        header: {
            'content-type': 'application/json',
        },
        data: {},
    },
    request (options = {}) {
        options.url = 'http://127.0.0.1:8088/thymeleaf' + options.url
        options.method = options.method || this.common.method
        options.header = options.header || this.common.header

      // eslint-disable-next-line promise/param-names
        return new Promise((res, rej) => {
            axios({
                ...options,
            }).then(function (response) {
                // console.log(response)
                if (options.native) return res(response)
                if (response.status !== 200) {
                    alert('请求失败')
                    return rej(response.data)
                }
                res(response.data)
            }).catch(function (e) {
                console.log(e)
                console.log('请求失败')
              // eslint-disable-next-line prefer-promise-reject-errors
                return rej()
            })
        })
    },
    get (url, data = {}, options = {}) {
        options.url = url
        options.data = data
        options.method = 'GET'
        return this.request(options)
    },
    post (url, data = {}, options = {}) {
        options.url = url
        options.data = data
        options.method = 'POST'
        return this.request(options)
    },
}
