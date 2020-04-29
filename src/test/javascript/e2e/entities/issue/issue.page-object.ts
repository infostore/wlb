import { element, by, ElementFinder } from 'protractor';

export class IssueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-issue div table .btn-danger'));
  title = element.all(by.css('jhi-issue div h2#page-heading span')).first();
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

export class IssueUpdatePage {
  pageTitle = element(by.id('jhi-issue-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  issueTypeSelect = element(by.id('field_issueType'));
  issueStatusSelect = element(by.id('field_issueStatus'));
  prioritySelect = element(by.id('field_priority'));
  resolutionSelect = element(by.id('field_resolution'));
  dueDateInput = element(by.id('field_dueDate'));

  projectSelect = element(by.id('field_project'));
  milestoneSelect = element(by.id('field_milestone'));

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

  async setIssueTypeSelect(issueType: string): Promise<void> {
    await this.issueTypeSelect.sendKeys(issueType);
  }

  async getIssueTypeSelect(): Promise<string> {
    return await this.issueTypeSelect.element(by.css('option:checked')).getText();
  }

  async issueTypeSelectLastOption(): Promise<void> {
    await this.issueTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setIssueStatusSelect(issueStatus: string): Promise<void> {
    await this.issueStatusSelect.sendKeys(issueStatus);
  }

  async getIssueStatusSelect(): Promise<string> {
    return await this.issueStatusSelect.element(by.css('option:checked')).getText();
  }

  async issueStatusSelectLastOption(): Promise<void> {
    await this.issueStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPrioritySelect(priority: string): Promise<void> {
    await this.prioritySelect.sendKeys(priority);
  }

  async getPrioritySelect(): Promise<string> {
    return await this.prioritySelect.element(by.css('option:checked')).getText();
  }

  async prioritySelectLastOption(): Promise<void> {
    await this.prioritySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setResolutionSelect(resolution: string): Promise<void> {
    await this.resolutionSelect.sendKeys(resolution);
  }

  async getResolutionSelect(): Promise<string> {
    return await this.resolutionSelect.element(by.css('option:checked')).getText();
  }

  async resolutionSelectLastOption(): Promise<void> {
    await this.resolutionSelect
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

  async milestoneSelectLastOption(): Promise<void> {
    await this.milestoneSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async milestoneSelectOption(option: string): Promise<void> {
    await this.milestoneSelect.sendKeys(option);
  }

  getMilestoneSelect(): ElementFinder {
    return this.milestoneSelect;
  }

  async getMilestoneSelectedOption(): Promise<string> {
    return await this.milestoneSelect.element(by.css('option:checked')).getText();
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

export class IssueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-issue-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-issue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
