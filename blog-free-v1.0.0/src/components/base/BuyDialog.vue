<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="380px"
  >
    <template v-slot:activator="{ on, attrs }">
      <div v-if="show">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          small
          label
          v-on="on"
        >
          <v-icon
            dark
            small
          >
            mdi-cart-plus
          </v-icon>
        </v-btn>
      </div>
      <div v-else>
        <v-btn
          depressed
          disabled
          small
          label
        >
          Disabled
          <!--          <v-icon-->
          <!--            dark-->
          <!--            small-->
          <!--          >-->
          <!--            mdi-cart-plus-->
          <!--          </v-icon>-->
        </v-btn>
      </div>
    </template>
    <v-card
      :loading="loading"
      class="mx-auto my-0"
    >
      <template slot="progress">
        <v-progress-linear
          color="deep-purple"
          height="40"
          indeterminate
        />
      </template>

      <v-img
        :src="require(`@/assets/articles/${value.img}`)"
        height="100%"
      />

      <v-card-title>{{ value.title }}</v-card-title>

      <v-card-text>
        <div class="my-4 subtitle-1">
          ￥ {{ value.price }}
        </div>
        <div>
          <v-btn
            class="mx-2"
            fab
            dark
            small
            color="primary"
            @click="minus"
          >
            <v-icon dark>
              mdi-minus
            </v-icon>
          </v-btn>
          {{ number }}
          <v-btn
            class="mx-2"
            fab
            dark
            small
            color="indigo"
            @click="plus"
          >
            <v-icon dark>
              mdi-plus
            </v-icon>
          </v-btn>
          <span class="ml-3 subtitle-3">库存：{{ value.stock }}</span>
          <span class="ml-3 subtitle-3">总计：￥{{ total }}</span>
        </div>
        <!--        <div>Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.</div>-->
      </v-card-text>

      <v-divider class="mx-4" />

      <v-card-actions>
        <v-spacer />
        <v-btn
          color="blue darken-1"
          text
          @click="dialog = false"
        >
          取消
        </v-btn>
        <v-btn
          color="blue darken-1"
          text
          @click="buy"
        >
          提交订单
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
  export default {
    name: 'BaseBuyDialog',
    props: {
      value: {
        type: Object,
        default: () => ({}),
      },
      status: {
        type: Number,
        required: true,
      },
    },
    data () {
      return {
        dialog: false,
        loading: false,
        selection: 1,
        number: 1,
      }
    },
    computed: {
      show: function () {
        // eslint-disable-next-line eqeqeq
        return this.status == 0
      },
      total: function () {
        // eslint-disable-next-line eqeqeq
        return this.number * this.value.price
      },
    },
    methods: {
      reserve () {
        this.loading = true
        setTimeout(() => (this.loading = false), 2000)
      },
      minus () {
        // eslint-disable-next-line eqeqeq
        this.number = this.number == 1 ? 1 : this.number - 1
      },
      plus () {
        // eslint-disable-next-line eqeqeq
        this.number = this.number == this.value.stock ? this.number : this.number + 1
      },
      buy () {
        var params = {
          orders_number: this.number, item_kill_id: this.value.killId,
        }
        this.$axios({
          method: 'post',
          url: 'http://127.0.0.1:8088/thymeleaf/buy',
          data: params,
        }).then(function (res) {
          // eslint-disable-next-line eqeqeq
          if (res.data == '超卖') {
            alert('商品发生超卖！')
          } else {
            alert('购买成功！')
          }
          this.$router.push('/order')
        }.bind(this)).catch(function (err) {
          console.log('err', err)
          alert('错误')
        })
      },
    },
  }
</script>

<style scoped>

</style>
