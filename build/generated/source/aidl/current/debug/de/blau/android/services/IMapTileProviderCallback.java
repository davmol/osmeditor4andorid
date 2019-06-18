/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/david/Code/android/osmeditor4android-master/src/main/aidl/de/blau/android/services/IMapTileProviderCallback.aidl
 */
package de.blau.android.services;
public interface IMapTileProviderCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements de.blau.android.services.IMapTileProviderCallback
{
private static final java.lang.String DESCRIPTOR = "de.blau.android.services.IMapTileProviderCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an de.blau.android.services.IMapTileProviderCallback interface,
 * generating a proxy if needed.
 */
public static de.blau.android.services.IMapTileProviderCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof de.blau.android.services.IMapTileProviderCallback))) {
return ((de.blau.android.services.IMapTileProviderCallback)iin);
}
return new de.blau.android.services.IMapTileProviderCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_mapTileLoaded:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
byte[] _arg4;
_arg4 = data.createByteArray();
this.mapTileLoaded(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_mapTileFailed:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.mapTileFailed(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements de.blau.android.services.IMapTileProviderCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void mapTileLoaded(java.lang.String rendererID, int zoomLevel, int tileX, int tileY, byte[] aImage) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(rendererID);
_data.writeInt(zoomLevel);
_data.writeInt(tileX);
_data.writeInt(tileY);
_data.writeByteArray(aImage);
mRemote.transact(Stub.TRANSACTION_mapTileLoaded, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void mapTileFailed(java.lang.String rendererID, int zoomLevel, int tileX, int tileY, int reason) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(rendererID);
_data.writeInt(zoomLevel);
_data.writeInt(tileX);
_data.writeInt(tileY);
_data.writeInt(reason);
mRemote.transact(Stub.TRANSACTION_mapTileFailed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_mapTileLoaded = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_mapTileFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void mapTileLoaded(java.lang.String rendererID, int zoomLevel, int tileX, int tileY, byte[] aImage) throws android.os.RemoteException;
public void mapTileFailed(java.lang.String rendererID, int zoomLevel, int tileX, int tileY, int reason) throws android.os.RemoteException;
}
