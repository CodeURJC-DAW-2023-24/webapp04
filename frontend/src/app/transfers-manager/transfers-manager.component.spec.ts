import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransfersManagerComponent } from './transfers-manager.component';

describe('TransfersManagerComponent', () => {
  let component: TransfersManagerComponent;
  let fixture: ComponentFixture<TransfersManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TransfersManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransfersManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
