# History

## summernote 설정

```sh
npm install ngx-summernote
npm install summernote
npm install jquery
```

```js
// webpack/webpack.common.js
module.exports = options => ({
  plugins: [
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery'
    })
  ]
});
```

```scss
// src/main/webapp/content/scss/vendor.scss
@import '~summernote/dist/summernote-lite.css';
```

```ts
// src/main/webapp/app/vendor.ts
import 'jquery/dist/jquery.min';
import 'summernote/dist/summernote-lite.min';
```

```ts
// src/main/webapp/app/shared/shared-libs.module.ts
import { NgxSummernoteModule } from 'ngx-summernote';

@NgModule({
  exports: [
    NgxSummernoteModule
  ]
})
```

## jqxwidget 설정

```sh
npm install jqwidgets-ng
npm install jqwidgets-scripts
```
