import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderErrorComponent } from './header-error.component';

describe('HeaderErrorComponent', () => {
  let component: HeaderErrorComponent;
  let fixture: ComponentFixture<HeaderErrorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HeaderErrorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HeaderErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
