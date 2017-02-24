import { WebtopupPage } from './app.po';

describe('webtopup App', function() {
  let page: WebtopupPage;

  beforeEach(() => {
    page = new WebtopupPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
