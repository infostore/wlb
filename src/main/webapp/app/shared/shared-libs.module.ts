import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { NgxSummernoteModule } from 'ngx-summernote';

import { jqxButtonModule } from 'jqwidgets-ng/jqxbuttons';
import { jqxComboBoxModule } from 'jqwidgets-ng/jqxcombobox';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  exports: [
    FormsModule,
    CommonModule,
    NgbModule,
    NgJhipsterModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    TranslateModule,
    NgxSummernoteModule,
    jqxButtonModule,
    jqxComboBoxModule,
    DropdownModule
  ]
})
export class WlbSharedLibsModule {}
