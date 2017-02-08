/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DashboardHelperService } from './dashboard-helper.service';

describe('DashboardHelperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardHelperService]
    });
  });

  it('should ...', inject([DashboardHelperService], (service: DashboardHelperService) => {
    expect(service).toBeTruthy();
  }));
});
