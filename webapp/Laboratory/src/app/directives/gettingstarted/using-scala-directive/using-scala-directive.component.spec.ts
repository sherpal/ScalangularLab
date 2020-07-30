import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsingScalaDirectiveComponent } from './using-scala-directive.component';

describe('UsingScalaDirectiveComponent', () => {
  let component: UsingScalaDirectiveComponent;
  let fixture: ComponentFixture<UsingScalaDirectiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsingScalaDirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsingScalaDirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
