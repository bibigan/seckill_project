<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="380px"
  >
    <template v-slot:activator="{ on, attrs }">
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
        :src="require(`@/assets/articles/${value.hero}`)"
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
          >
            <v-icon dark>
              mdi-minus
            </v-icon>
          </v-btn>
          1
          <v-btn
            class="mx-2"
            fab
            dark
            small
            color="indigo"
          >
            <v-icon dark>
              mdi-plus
            </v-icon>
          </v-btn>
        </div>
<!--        <div>Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.</div>-->
      </v-card-text>

      <v-divider class="mx-4" />

      <v-card-actions>
        <v-spacer></v-spacer>
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
          @click="dialog = false"
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
    data () {
      return {
        dialog: false,
        loading: false,
        selection: 1,
      }
    },
    props: {
      value: {
        type: Object,
        default: () => ({}),
      },
    },
    methods: {
      reserve () {
        this.loading = true
        setTimeout(() => (this.loading = false), 2000)
      },
    },
  }
</script>

<style scoped>

</style>
