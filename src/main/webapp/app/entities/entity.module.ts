import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.WlbProjectModule)
      },
      {
        path: 'milestone',
        loadChildren: () => import('./milestone/milestone.module').then(m => m.WlbMilestoneModule)
      },
      {
        path: 'issue',
        loadChildren: () => import('./issue/issue.module').then(m => m.WlbIssueModule)
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.WlbCommentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WlbEntityModule {}
