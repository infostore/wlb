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
      },
      {
        path: 'label-group',
        loadChildren: () => import('./label-group/label-group.module').then(m => m.WlbLabelGroupModule)
      },
      {
        path: 'label',
        loadChildren: () => import('./label/label.module').then(m => m.WlbLabelModule)
      },
      {
        path: 'issue-label',
        loadChildren: () => import('./issue-label/issue-label.module').then(m => m.WlbIssueLabelModule)
      },
      {
        path: 'issue-assignee',
        loadChildren: () => import('./issue-assignee/issue-assignee.module').then(m => m.WlbIssueAssigneeModule)
      },
      {
        path: 'issue-watcher',
        loadChildren: () => import('./issue-watcher/issue-watcher.module').then(m => m.WlbIssueWatcherModule)
      },
      {
        path: 'attachment',
        loadChildren: () => import('./attachment/attachment.module').then(m => m.WlbAttachmentModule)
      },
      {
        path: 'issue-attachment',
        loadChildren: () => import('./issue-attachment/issue-attachment.module').then(m => m.WlbIssueAttachmentModule)
      },
      {
        path: 'project-activity',
        loadChildren: () => import('./project-activity/project-activity.module').then(m => m.WlbProjectActivityModule)
      },
      {
        path: 'issue-activity',
        loadChildren: () => import('./issue-activity/issue-activity.module').then(m => m.WlbIssueActivityModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WlbEntityModule {}
