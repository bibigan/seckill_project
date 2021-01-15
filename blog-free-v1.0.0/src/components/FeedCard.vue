<template>
  <v-col
    cols="12"
    :md="size === 2 ? 6 : size === 3 ? 3 : undefined"
  >
    <base-card
      :height="value.prominent ? 450 : 300"
      color="grey lighten-1"
      dark
      href="#!"
    >
      <v-img
        :src="require(`@/assets/articles/${value.hero}`)"
        height="100%"
        gradient="rgba(0, 0, 0, .42), rgba(0, 0, 0, .42)"
      >
        <v-row
          v-if="!value.prominent"
          class="fill-height text-right ma-0"
        >
          <v-col cols="12">
            <v-chip
              label
              class="mx-0 mb-2 text-uppercase"
              color="grey darken-3"
              text-color="white"
              small
              @click.stop=""
            >
              <base-time-count
                :end-time=" value.seckillEndTime"
                :start-time="value.seckillStartTime"
                 style="color: aqua"
               />
            </v-chip>

            <h3 class="title font-weight-bold mb-2">
              {{ value.title }}
            </h3>

            <div class="caption">
              ￥{{ value.price }}<br>
            </div>
          </v-col>

          <v-col align-self="end">
<!--            <v-chip-->
<!--              class="text-uppercase ma-0"-->
<!--              color="primary"-->
<!--              label-->
<!--              small-->
<!--              @click.stop=""-->
<!--            >-->
<!--              <v-icon-->
<!--                dark-->
<!--                small-->
<!--              >-->
<!--                mdi-cart-plus-->
<!--              </v-icon>-->
<!--            </v-chip>-->
            <base-buy-dialog
              :value="value"
            ></base-buy-dialog>
          </v-col>
        </v-row>
      </v-img>
    </base-card>
  </v-col>
</template>

<script>
  import BaseTimeCount from './base/TimeCount'
  import BaseBuyDialog from './base/BuyDialog'
  export default {
    name: 'FeedCard',
    components: {
      BaseBuyDialog,
      BaseTimeCount,

    },
    props: {
      size: {
        type: Number,
        required: true,
      },
      value: {
        type: Object,
        default: () => ({}),
      },
      status: {
        // eslint-disable-next-line no-undef
        type: Boolean,
      },
    },
    computed: {
      // eslint-disable-next-line vue/no-dupe-keys,vue/return-in-computed-property
      status () {
        // eslint-disable-next-line no-unused-vars
        const startTime = new Date(this.value.seckillStartTime)
        // eslint-disable-next-line no-unused-vars
        const endTime = new Date(this.value.seckillEndTime)
        // eslint-disable-next-line no-unused-vars
        const nowTime = new Date()
        if ((nowTime.getTime() - endTime.getTime()) > 0) {
          return false
        } else if (status ^ ((nowTime.getTime() - startTime.getTime()) < 0)) {
          return true
        } else {
          return false
        }
      },
    },
    mounted () {

    },
    methods: {
    },
  }
</script>

<style>
.v-image__image {
  transition: .3s linear;
}
</style>
