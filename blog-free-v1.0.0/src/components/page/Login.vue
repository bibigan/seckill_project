<template>
  <v-card
    class="mx-auto my-12 outer-card"
    max-width="374"
  >
    <v-card-title>
      <v-card
        class="v-header py-2 mx-auto my-n12"
        max-width="364"
        raised
      >
        <v-card-title>
          <v-row
            class="color"
            justify="center"
          >
            <h4>Login</h4>
          </v-row>
        </v-card-title>
        <v-card-actions>
          <v-row
            class="color"
            justify="center"
          >
            <v-btn
              icon
              color="white"
            >
              <v-icon>mdi-facebook</v-icon>
            </v-btn>
            <v-btn
              icon
              color="white"
            >
              <v-icon>mdi-twitter</v-icon>
            </v-btn>
            <v-btn
              icon
              color="white"
            >
              <v-icon>mdi-google-plus</v-icon>
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-card-title><br>
    <v-form
      ref="form"
      v-model="valid"
      lazy-validation
    >
      <v-card-text class="my-4">
        <v-text-field
          ref="name"
          v-model="name"
          :rules="[() => !!name || '必填项']"
          :error-messages="errorMessages"
          label="用户名"
          required
          prepend-icon="mdi-face"
          color="purple"
          clearable
        />
        <!--      <v-text-field-->
        <!--        ref="email"-->
        <!--        v-model="email"-->
        <!--        :rules="[() => !!email || '必填项']"-->
        <!--        :error-messages="errorMessages"-->
        <!--        label="邮箱"-->
        <!--        required-->
        <!--        prepend-icon="mdi-email"-->
        <!--        color="purple"-->
        <!--      />-->
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
          prepend-icon="mdi-lock-outline"
          color="purple"
          clearable
        />
        <!--      </v-card-title>-->
        <v-card-actions>
          <v-row justify="center">
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
              normal
              text
              color="success"
              @click="submit"
            >
              登录
            </v-btn>
            <v-btn
              normal
              text
              href="/signup"
              color="blue"
            >
              还没有账号？去注册
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card-text>
    </v-form>
  </v-card>
</template>

<script>
  // eslint-disable-next-line import/no-duplicates,no-unused-vars
  import { mapMutations, mapGetters, mapState } from 'vuex'
  export default {
    name: 'LoginVue',
    data: () => ({
      errorMessages: '',
      name: null,
      formHasErrors: false,
      password: null,
      // email: null,
      userToken: '',
      valid: false, // 表单校验结果标记
    }),
    computed: {
      form () {
        return {
          name: this.name,
          password: this.password,
          // email: this.email,
        }
      },
    },

    watch: {
      name () {
        this.errorMessages = ''
      },
    },

    methods: {
      ...mapMutations(['changeLogin']),
      ...mapMutations(['setUserName']),
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
        console.log('valid:' + this.valid + ', formHasErrors:' + this.formHasErrors)
        // eslint-disable-next-line eqeqeq
        if (this.formHasErrors == false && this.valid == true) {
          this.submitForm()
        } else {
          alert('请完善信息')
        }
      },
      login () {
        // eslint-disable-next-line no-unused-vars
        var params = {
          user_name: this.name, user_password: this.password,
        }
        // eslint-disable-next-line no-undef
        this.$axios.post('http://127.0.0.1:8088/thymeleaf/login', params)
          .then(response => {
            const { data } = response
            // console.log('data:' + data)
            // eslint-disable-next-line eqeqeq
            if (data == '0') {
              location.href = 'home'
              // eslint-disable-next-line eqeqeq
            } else if (data == '1') {
              alert('账号密码错误')
            } else {
              console.log('访问失败，data：' + data)
            }
          })
      },
      submitForm () {
        const v = this
        var params = {
          user_name: this.name, user_password: this.password,
        }
        this.$axios({
          method: 'post',
          url: 'http://127.0.0.1:8088/thymeleaf/login',
          data: params,
        }).then(function (res) {
          // eslint-disable-next-line eqeqeq
          if (res.data == 'error') {
            alert('密码或用户名错误')
          } else {
            v.userToken = res.data.token
            console.log('token：' + v.userToken)
            // 将用户token保存到vuex中
            v.setUserName(params.user_name)
            v.changeLogin({ Authorization: v.userToken })
            alert('登录成功!')
            v.$router.push('/home')
          }
        }).catch(function (err) {
          console.log('err', err)
          alert('错误')
        })
      },
    },

  }
</script>

<style scoped>

</style>
<style>
  .v-header{
    background-image:linear-gradient(60deg,#66bb6a,#388e3c);
    text-align: center;
    width: 100%;
    margin-top: -40px;
  }
  h4{
    font-family: "Times New Roman", Times, serif;
    color: white;
  }
  .outer-card{
    border-radius: 8px !important;
  }
  .v-input__icon--prepend .v-icon {
    color: black;
  }
</style>
