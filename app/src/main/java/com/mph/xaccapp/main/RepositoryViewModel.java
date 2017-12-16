package com.mph.xaccapp.main;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RepositoryViewModel {

    public abstract String id();
    public abstract String title();
    public abstract String description();
    public abstract String login();
    public abstract String url();
    public abstract String ownerUrl();
    public abstract boolean fork();

    public static RepositoryViewModel create(String id, String title, String description,
                                             String login, String url, String ownerUrl,
                                             boolean fork) {
        return builder()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setLogin(login)
                .setUrl(url)
                .setOwnerUrl(ownerUrl)
                .setFork(fork)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_RepositoryViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setTitle(String value);
        public abstract Builder setDescription(String value);
        public abstract Builder setLogin(String value);
        public abstract Builder setUrl(String value);
        public abstract Builder setOwnerUrl(String value);
        public abstract Builder setFork(boolean value);

        public abstract RepositoryViewModel build();
    }
}
