package ru.vinhome.model;

import java.time.LocalDateTime;

public class Message {
    final private Long id;
    final private Long idSender;
    final private Long idRecipient;
    final private String message;
    final private LocalDateTime createdAt;

    private Message(Long id, Long idSender, Long idRecipient, String message, LocalDateTime createdAt) {
        this.id = id;
        this.idSender = idSender;
        this.idRecipient = idRecipient;
        this.message = message;
        this.createdAt = createdAt;
    }

    /*
        public static Message createMessage(Long id,
                                            Long idSender,
                                            Long idRecipient,
                                            String message,
                                            LocalDateTime dateTime) {
            return new Message(
                    id,
                    idSender,
                    idRecipient,
                    message,
                    dateTime
            );
        }
    */
    public static Message createMessage(Long id,
                                        Long idSender,
                                        Long idRecipient,
                                        String message) {
        return new Message(
                id,
                idSender,
                idRecipient,
                message,
                LocalDateTime.now()
        );
    }

    public Long getId() {
        return this.id;
    }

    public Long getIdSender() {
        return this.idSender;
    }

    public Long getIdRecipient() {
        return this.idRecipient;
    }

    public String getMessage() {
        return this.message;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$idSender = this.getIdSender();
        final Object other$idSender = other.getIdSender();
        if (this$idSender == null ? other$idSender != null : !this$idSender.equals(other$idSender)) return false;
        final Object this$idRecipient = this.getIdRecipient();
        final Object other$idRecipient = other.getIdRecipient();
        if (this$idRecipient == null ? other$idRecipient != null : !this$idRecipient.equals(other$idRecipient))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $idSender = this.getIdSender();
        result = result * PRIME + ($idSender == null ? 43 : $idSender.hashCode());
        final Object $idRecipient = this.getIdRecipient();
        result = result * PRIME + ($idRecipient == null ? 43 : $idRecipient.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "Message(id=" + this.getId() + ", idSender=" + this.getIdSender() + ", idRecipient=" + this.getIdRecipient() + ", message=" + this.getMessage() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}

