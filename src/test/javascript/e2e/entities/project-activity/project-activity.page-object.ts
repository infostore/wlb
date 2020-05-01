import { element, by, ElementFinder } from 'protractor';

export class ProjectActivityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-project-activity div table .btn-danger'));
  title = element.all(by.css('jhi-project-activity div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProjectActivityUpdatePage {
  pageTitle = element(by.id('jhi-project-activity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  activityInput = element(by.id('field_activity'));

  projectSelect = element(by.id('field_project'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setActivityInput(activity: string): Promise<void> {
    await this.activityInput.sendKeys(activity);
  }

  async getActivityInput(): Promise<string> {
    return await this.activityInput.getAttribute('value');
  }

  async projectSelectLastOption(): Promise<void> {
    await this.projectSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async projectSelectOption(option: string): Promise<void> {
    await this.projectSelect.sendKeys(option);
  }

  getProjectSelect(): ElementFinder {
    return this.projectSelect;
  }

  async getProjectSelectedOption(): Promise<string> {
    return await this.projectSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProjectActivityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-projectActivity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-projectActivity'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
