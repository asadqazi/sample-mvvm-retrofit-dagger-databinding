package es.amarinag.mvvm_databinging_dagger2.ui.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import es.amarinag.mvvm_databinging_dagger2.domain.usecase.DetailRepositoryUseCase;
import es.amarinag.mvvm_databinging_dagger2.model.Repository;
import javax.inject.Inject;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by AMarinaG on 28/04/2016.
 */
public class RepositoryDetailViewModel extends BaseObservable {
  private ObservableField<Repository> repository = new ObservableField<>();
  private ObservableBoolean showLoading = new ObservableBoolean(true);

  @Inject
  DetailRepositoryUseCase detailRepositoryUseCase;

  @Inject
  public RepositoryDetailViewModel() {
  }

  public ObservableField<Repository> getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository.set(repository);
  }

  public Repository getRepositoryData() {
    return repository.get();
  }

  public void refreshData(String owner, String repository) {
    Observable<Repository> repositoryObservable = detailRepositoryUseCase.invoke(owner, repository);
    repositoryObservable.subscribe(repo -> {
          Timber.wtf("repo: " + repo);
          setRepository(repo);
          setShowLoading(false);
        }, throwable -> Timber.e(throwable.getMessage(), throwable)
    );
  }

  public ObservableBoolean getShowLoading() {
    return showLoading;
  }

  public void setShowLoading(boolean showLoading) {
    this.showLoading.set(showLoading);
  }
}
