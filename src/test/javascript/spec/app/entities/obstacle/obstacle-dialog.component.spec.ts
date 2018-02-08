/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServeurTestModule } from '../../../test.module';
import { ObstacleDialogComponent } from '../../../../../../main/webapp/app/entities/obstacle/obstacle-dialog.component';
import { ObstacleService } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.service';
import { Obstacle } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.model';

describe('Component Tests', () => {

    describe('Obstacle Management Dialog Component', () => {
        let comp: ObstacleDialogComponent;
        let fixture: ComponentFixture<ObstacleDialogComponent>;
        let service: ObstacleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [ObstacleDialogComponent],
                providers: [
                    ObstacleService
                ]
            })
            .overrideTemplate(ObstacleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObstacleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObstacleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Obstacle('123');
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.obstacle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'obstacleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Obstacle();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.obstacle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'obstacleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
