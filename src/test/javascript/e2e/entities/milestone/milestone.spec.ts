import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MilestoneComponentsPage, MilestoneDeleteDialog, MilestoneUpdatePage } from './milestone.page-object';

const expect = chai.expect;

describe('Milestone e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let milestoneComponentsPage: MilestoneComponentsPage;
  let milestoneUpdatePage: MilestoneUpdatePage;
  let milestoneDeleteDialog: MilestoneDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Milestones', async () => {
    await navBarPage.goToEntity('milestone');
    milestoneComponentsPage = new MilestoneComponentsPage();
    await browser.wait(ec.visibilityOf(milestoneComponentsPage.title), 5000);
    expect(await milestoneComponentsPage.getTitle()).to.eq('wlbApp.milestone.home.title');
    await browser.wait(ec.or(ec.visibilityOf(milestoneComponentsPage.entities), ec.visibilityOf(milestoneComponentsPage.noResult)), 1000);
  });

  it('should load create Milestone page', async () => {
    await milestoneComponentsPage.clickOnCreateButton();
    milestoneUpdatePage = new MilestoneUpdatePage();
    expect(await milestoneUpdatePage.getPageTitle()).to.eq('wlbApp.milestone.home.createOrEditLabel');
    await milestoneUpdatePage.cancel();
  });

  it('should create and save Milestones', async () => {
    const nbButtonsBeforeCreate = await milestoneComponentsPage.countDeleteButtons();

    await milestoneComponentsPage.clickOnCreateButton();

    await promise.all([
      milestoneUpdatePage.setNameInput('name'),
      milestoneUpdatePage.setDescriptionInput('description'),
      milestoneUpdatePage.milestoneStatusSelectLastOption(),
      milestoneUpdatePage.setDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      milestoneUpdatePage.projectSelectLastOption()
    ]);

    expect(await milestoneUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await milestoneUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await milestoneUpdatePage.getDueDateInput()).to.contain('2001-01-01T02:30', 'Expected dueDate value to be equals to 2000-12-31');

    await milestoneUpdatePage.save();
    expect(await milestoneUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await milestoneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Milestone', async () => {
    const nbButtonsBeforeDelete = await milestoneComponentsPage.countDeleteButtons();
    await milestoneComponentsPage.clickOnLastDeleteButton();

    milestoneDeleteDialog = new MilestoneDeleteDialog();
    expect(await milestoneDeleteDialog.getDialogTitle()).to.eq('wlbApp.milestone.delete.question');
    await milestoneDeleteDialog.clickOnConfirmButton();

    expect(await milestoneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
