<template>
  <v-container>
<!--    商品-->
    <v-row>
      <v-col cols="12">
        <slot />
      </v-col>

      <feed-card
        v-for="(article, i) in paginatedArticles"
        :key="article.title"
        :size="layout[i]"
        :value="article"
      />
    </v-row>
<!--页码-->
    <v-row align="center">
      <v-col cols="3">
        <base-btn
          v-if="page !== 1"
          class="ml-0"
          square
          title="Previous page"
          @click="page--"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </base-btn>
      </v-col>

      <v-col
        class="text-center subheading"
        cols="6"
      >
        第 {{ page }} 页 | 共 {{ pages }} 页
      </v-col>

      <v-col
        class="text-right"
        cols="3"
      >
        <base-btn
          v-if="pages > 1 && page < pages"
          class="mr-0"
          square
          title="下一页"
          @click="page++"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </base-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  // Utilities
  import {
    mapState,
  } from 'vuex'

  export default {
    name: 'Feed',

    components: {
      FeedCard: () => import('@/components/FeedCard'),
    },

    data: () => ({
      // 每行卡片的个数
      layout:[3, 3, 3, 3, 3, 3,3,3],
      page: 1,
      count: 8,
      // 每页6个
    }),

    computed: {
      ...mapState(['articles']),
      pages () {
        return Math.ceil(this.articles.length / this.count)
      },
      paginatedArticles () {
        const start = (this.page - 1) * this.count
        const stop = this.page * this.count

        return this.articles.slice(start, stop)
      },
    },

    watch: {
      page () {
        window.scrollTo(0, this.windowScroll())
      },
    },

    mounted () {
      window.addEventListener('scroll', this.windowScroll)
    },
    methods: {
      windowScroll () {
        // 滚动条距离页面顶部的距离
        // 以下写法原生兼容
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
        return scrollTop
      },
    },
    beforeDestroy () {
      window.removeEventListener('scroll', this.windowScroll)
    },
  }
</script>
