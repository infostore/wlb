import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProjectActivityComponentsPage, ProjectActivityDeleteDialog, ProjectActivityUpdatePage } from './project-activity.page-object';

const expect = chai.expect;

describe('ProjectActivity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let projectActivityComponentsPage: ProjectActivityComponentsPage;
  let projectActivityUpdatePage: ProjectActivityUpdatePage;
  let projectActivityDeleteDialog: ProjectActivityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProjectActivities', async () => {
    await navBarPage.goToEntity('project-activity');
    projectActivityComponentsPage = new ProjectActivityComponentsPage();
    await browser.wait(ec.visibilityOf(projectActivityComponentsPage.title), 5000);
    expect(await projectActivityComponentsPage.getTitle()).to.eq('wlbApp.projectActivity.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(projectActivityComponentsPage.entities), ec.visibilityOf(projectActivityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProjectActivity page', async () => {
    await projectActivityComponentsPage.clickOnCreateButton();
    projectActivityUpdatePage = new ProjectActivityUpdatePage();
    expect(await projectActivityUpdatePage.getPageTitle()).to.eq('wlbApp.projectActivity.home.createOrEditLabel');
    await projectActivityUpdatePage.cancel();
  });

  it('should create and save ProjectActivities', async () => {
    const nbButtonsBeforeCreate = await projectActivityComponentsPage.countDeleteButtons();

    await projectActivityComponentsPage.clickOnCreateButton();

    await promise.all([projectActivityUpdatePage.setActivityInput('activity'), projectActivityUpdatePage.projectSelectLastOption()]);

    expect(await projectActivityUpdatePage.getActivityInput()).to.eq('activity', 'Expected Activity value to be equals to activity');

    await projectActivityUpdatePage.save();
    expect(await projectActivityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await projectActivityComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProjectActivity', async () => {
    const nbButtonsBeforeDelete = await projectActivityComponentsPage.countDeleteButtons();
    await projectActivityComponentsPage.clickOnLastDeleteButton();

    projectActivityDeleteDialog = new ProjectActivityDeleteDialog();
    expect(await projectActivityDeleteDialog.getDialogTitle()).to.eq('wlbApp.projectActivity.delete.question');
    await projectActivityDeleteDialog.clickOnConfirmButton();

    expect(await projectActivityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
