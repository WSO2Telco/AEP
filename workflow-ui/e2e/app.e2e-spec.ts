import { WorkflowManagerUiPage } from './app.po';

describe('workflow-manager-ui App', function() {
  let page: WorkflowManagerUiPage;

  beforeEach(() => {
    page = new WorkflowManagerUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
