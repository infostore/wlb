import { element, by, ElementFinder } from 'protractor';

export class MilestoneComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-milestone div table .btn-danger'));
  title = element.all(by.css('jhi-milestone div h2#page-heading span')).first();
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

export class MilestoneUpdatePage {
  pageTitle = element(by.id('jhi-milestone-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  milestoneStatusSelect = element(by.id('field_milestoneStatus'));
  dueDateInput = element(by.id('field_dueDate'));

  projectSelect = element(by.id('field_project'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setMilestoneStatusSelect(milestoneStatus: string): Promise<void> {
    await this.milestoneStatusSelect.sendKeys(milestoneStatus);
  }

  async getMilestoneStatusSelect(): Promise<string> {
    return await this.milestoneStatusSelect.element(by.css('option:checked')).getText();
  }

  async milestoneStatusSelectLastOption(): Promise<void> {
    await this.milestoneStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDueDateInput(dueDate: string): Promise<void> {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput(): Promise<string> {
    return await this.dueDateInput.getAttribute('value');
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

export class MilestoneDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-milestone-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-milestone'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
