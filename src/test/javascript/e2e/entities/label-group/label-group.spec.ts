import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LabelGroupComponentsPage, LabelGroupDeleteDialog, LabelGroupUpdatePage } from './label-group.page-object';

const expect = chai.expect;

describe('LabelGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let labelGroupComponentsPage: LabelGroupComponentsPage;
  let labelGroupUpdatePage: LabelGroupUpdatePage;
  let labelGroupDeleteDialog: LabelGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LabelGroups', async () => {
    await navBarPage.goToEntity('label-group');
    labelGroupComponentsPage = new LabelGroupComponentsPage();
    await browser.wait(ec.visibilityOf(labelGroupComponentsPage.title), 5000);
    expect(await labelGroupComponentsPage.getTitle()).to.eq('wlbApp.labelGroup.home.title');
    await browser.wait(ec.or(ec.visibilityOf(labelGroupComponentsPage.entities), ec.visibilityOf(labelGroupComponentsPage.noResult)), 1000);
  });

  it('should load create LabelGroup page', async () => {
    await labelGroupComponentsPage.clickOnCreateButton();
    labelGroupUpdatePage = new LabelGroupUpdatePage();
    expect(await labelGroupUpdatePage.getPageTitle()).to.eq('wlbApp.labelGroup.home.createOrEditLabel');
    await labelGroupUpdatePage.cancel();
  });

  it('should create and save LabelGroups', async () => {
    const nbButtonsBeforeCreate = await labelGroupComponentsPage.countDeleteButtons();

    await labelGroupComponentsPage.clickOnCreateButton();

    await promise.all([labelGroupUpdatePage.setNameInput('name')]);

    expect(await labelGroupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await labelGroupUpdatePage.save();
    expect(await labelGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await labelGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LabelGroup', async () => {
    const nbButtonsBeforeDelete = await labelGroupComponentsPage.countDeleteButtons();
    await labelGroupComponentsPage.clickOnLastDeleteButton();

    labelGroupDeleteDialog = new LabelGroupDeleteDialog();
    expect(await labelGroupDeleteDialog.getDialogTitle()).to.eq('wlbApp.labelGroup.delete.question');
    await labelGroupDeleteDialog.clickOnConfirmButton();

    expect(await labelGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
