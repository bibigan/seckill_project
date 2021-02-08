<template>
  <v-row justify="center">
    <v-col
      cols="12"
      sm="10"
      md="8"
      lg="6"
    >
      <v-card ref="form">
        <v-card-text>
          <v-text-field
            ref="name"
            v-model="name"
            :rules="[() => !!name || '必填项']"
            :error-messages="errorMessages"
            label="用户名"
            required
          />
          <v-text-field
            ref="email"
            v-model="email"
            :rules="[() => !!email || '必填项']"
            :error-messages="errorMessages"
            label="邮箱"
            required
          />
          <v-text-field
            ref="password"
            v-model="password"
            :rules="[() => !!password || '必填项',
                     () => !!password && password.length <= 10 || '密码不多于10位',
                     () => !!password && password.length > 4 || '密码不少于5位']"
            :error-messages="errorMessages"
            label="密码"
            counter="10"
            type="password"
            required
          />
          <v-text-field
            ref="rePassword"
            v-model="rePassword"
            :rules="[() => !!rePassword || '必填项',
                     () => !!rePassword && rePassword.length <= 10 || '密码不多于10位',
                     () => !!rePassword && rePassword.length > 4 || '密码不少于5位',
                     () => rePassword == password || '密码不一致',
            ]"
            :error-messages="errorMessages"
            label="重新输入密码"
            counter="10"
            type="password"
            required
          />
        </v-card-text>
        <!--        <v-divider class="mt-12" />-->
        <v-card-actions>
          <v-btn
            text
            href="/home"
          >
            取消
          </v-btn>
          <v-spacer />
          <v-slide-x-reverse-transition>
            <v-tooltip
              v-if="formHasErrors"
              left
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  class="my-0"
                  v-bind="attrs"
                  @click="resetForm"
                  v-on="on"
                >
                  <v-icon>mdi-refresh</v-icon>
                </v-btn>
              </template>
              <span>清空</span>
            </v-tooltip>
          </v-slide-x-reverse-transition>
          <v-btn
            color="primary"
            text
            @click="submit"
          >
            注册
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>

  export default {
    name: 'SignupVue',
    data: () => ({
      errorMessages: '',
      name: null,
      formHasErrors: false,
      password: null,
      rePassword: null,
      email: null,
    }),

    computed: {
      form () {
        return {
          name: this.name,
          password: this.password,
          rePassword: this.rePassword,
          email: this.email,
        }
      },
    },

    watch: {
      name () {
        this.errorMessages = ''
      },
    },

    methods: {

      resetForm () {
        this.errorMessages = []
        this.formHasErrors = false

        Object.keys(this.form).forEach(f => {
          this.$refs[f].reset()
        })
      },
      submit () {
        this.formHasErrors = false

        Object.keys(this.form).forEach(f => {
          if (!this.form[f]) this.formHasErrors = true

          this.$refs[f].validate(true)
        })
        // 通过 axios发送user对象到 forelogin
        // 3. 如果登陆成功，则跳转到首页 home, 否则显示错误信息
        this.login1()
      },
      login1 () {
        // eslint-disable-next-line no-unused-vars
        var params = {
          user_name: this.name, user_password: this.password, user_email: this.email,
        }
        // eslint-disable-next-line no-undef
        // console.log('email:' + this.email)
        this.$axios.post('http://127.0.0.1:8088/thymeleaf/register', params)
          .then(response => {
            const { data } = response
            // console.log('data:' + data)
            // eslint-disable-next-line eqeqeq
            if (data == '0') {
              location.href = 'home'
              // eslint-disable-next-line eqeqeq
            } else if (data == '1') {
              alert('用户名' + this.name + '已经被使用,不能使用')
            } else {
              console.log('访问失败，data：' + data)
            }
          })
      },
      login () {
        // eslint-disable-next-line no-unused-vars
        var params = {
          user_name: this.name, user_password: this.password, user_email: this.email,
        }
        const res = this.$H.post('/register', params)
        // eslint-disable-next-line no-const-assign
        console.log('!!res:' + res.code)
        // eslint-disable-next-line eqeqeq
        if (res == '0') {
          location.href = 'home'
          // eslint-disable-next-line eqeqeq
        } else if (res == '1') {
          alert('用户名' + this.users.name + '已经被使用,不能使用')
        } else {
          console.log('访问失败，res：' + res)
        }
      },
    },
  }
</script>

<style scoped>

</style>
