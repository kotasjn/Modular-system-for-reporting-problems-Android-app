package cz.jankotas.bakalarka.models

import androidx.annotation.NonNull
import androidx.annotation.Nullable

data class APILoginResponse constructor(@NonNull val error:Boolean,
                                        @Nullable val message:String,
                                        @Nullable val access_token: String,
                                        @Nullable val token_type: String,
                                        @Nullable val expires_at: String,
                                        @Nullable val user: User)