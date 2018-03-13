/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServeurTestModule } from '../../../test.module';
import { ObstacleDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/obstacle/obstacle-delete-dialog.component';
import { ObstacleService } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.service';

describe('Component Tests', () => {

    describe('Obstacle Management Delete Component', () => {
        let comp: ObstacleDeleteDialogComponent;
        let fixture: ComponentFixture<ObstacleDeleteDialogComponent>;
        let service: ObstacleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [ObstacleDeleteDialogComponent],
                providers: [
                    ObstacleService
                ]
            })
            .overrideTemplate(ObstacleDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObstacleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObstacleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
