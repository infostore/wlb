import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProjectMemberComponentsPage, ProjectMemberDeleteDialog, ProjectMemberUpdatePage } from './project-member.page-object';

const expect = chai.expect;

describe('ProjectMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let projectMemberComponentsPage: ProjectMemberComponentsPage;
  let projectMemberUpdatePage: ProjectMemberUpdatePage;
  let projectMemberDeleteDialog: ProjectMemberDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProjectMembers', async () => {
    await navBarPage.goToEntity('project-member');
    projectMemberComponentsPage = new ProjectMemberComponentsPage();
    await browser.wait(ec.visibilityOf(projectMemberComponentsPage.title), 5000);
    expect(await projectMemberComponentsPage.getTitle()).to.eq('wlbApp.projectMember.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(projectMemberComponentsPage.entities), ec.visibilityOf(projectMemberComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProjectMember page', async () => {
    await projectMemberComponentsPage.clickOnCreateButton();
    projectMemberUpdatePage = new ProjectMemberUpdatePage();
    expect(await projectMemberUpdatePage.getPageTitle()).to.eq('wlbApp.projectMember.home.createOrEditLabel');
    await projectMemberUpdatePage.cancel();
  });

  it('should create and save ProjectMembers', async () => {
    const nbButtonsBeforeCreate = await projectMemberComponentsPage.countDeleteButtons();

    await projectMemberComponentsPage.clickOnCreateButton();

    await promise.all([
      projectMemberUpdatePage.roleSelectLastOption(),
      projectMemberUpdatePage.userSelectLastOption(),
      projectMemberUpdatePage.projectSelectLastOption()
    ]);

    await projectMemberUpdatePage.save();
    expect(await projectMemberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await projectMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProjectMember', async () => {
    const nbButtonsBeforeDelete = await projectMemberComponentsPage.countDeleteButtons();
    await projectMemberComponentsPage.clickOnLastDeleteButton();

    projectMemberDeleteDialog = new ProjectMemberDeleteDialog();
    expect(await projectMemberDeleteDialog.getDialogTitle()).to.eq('wlbApp.projectMember.delete.question');
    await projectMemberDeleteDialog.clickOnConfirmButton();

    expect(await projectMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
