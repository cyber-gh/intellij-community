package com.intellij.localvcs;

import static com.intellij.localvcs.Difference.Kind.*;

import java.io.IOException;

public class FileEntry extends Entry {
  private Content myContent;

  public FileEntry(Integer id, String name, Content content, Long timestamp) {
    super(id, name, timestamp);
    myContent = content;
  }

  public FileEntry(Stream s) throws IOException {
    super(s);
    myContent = s.readContent();
  }

  @Override
  public void write(Stream s) throws IOException {
    super.write(s);
    s.writeContent(myContent);
  }

  @Override
  public Content getContent() {
    return myContent;
  }

  @Override
  public FileEntry copy() {
    return new FileEntry(myId, myName, myContent, myTimestamp);
  }

  @Override
  public void changeContent(Content newContent, Long newTimestamp) {
    myContent = newContent;
    myTimestamp = newTimestamp;
  }

  @Override
  public Difference getDifferenceWith(Entry e) {
    boolean modified = !myName.equals(e.getName()) || !myContent.equals(e.getContent());
    return new Difference(true, modified ? MODIFIED : NOT_MODIFIED, this, e);
  }

  @Override
  protected Difference asCreatedDifference() {
    return new Difference(true, CREATED, null, this);
  }

  @Override
  protected Difference asDeletedDifference() {
    return new Difference(true, DELETED, this, null);
  }
}
